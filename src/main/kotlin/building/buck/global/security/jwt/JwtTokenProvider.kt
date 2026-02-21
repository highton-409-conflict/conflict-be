package building.buck.global.security.jwt

import building.buck.global.security.auth.AuthDetailsService
import building.buck.global.security.jwt.exception.ExpiredJwtException
import building.buck.global.security.jwt.exception.InvalidJwtException
import building.buck.global.security.jwt.refresh.RefreshToken
import building.buck.global.security.jwt.refresh.repository.RefreshTokenRepository
import building.buck.global.security.jwt.response.TokenResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }
    fun generateToken(accountId: String): TokenResponse {
        val accessToken = generateAccessToken(accountId)
        val refreshToken = generateRefreshToken()
        refreshTokenRepository.save(
            RefreshToken(accountId, refreshToken, jwtProperties.refreshExp),
        )
        return TokenResponse(accessToken, refreshToken)
    }

    fun reIssue(refreshToken: String): TokenResponse {
        if (!isRefreshToken(refreshToken)) {
            throw InvalidJwtException
        }

        refreshTokenRepository
            .findByToken(refreshToken)
            ?.let { token ->
                val id = token.id

                val tokenResponse = generateToken(id)
                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
            } ?: throw InvalidJwtException
    }

    private fun isRefreshToken(token: String?): Boolean = REFRESH_KEY == getJws(token!!).get("type", String::class.java)

    private fun generateAccessToken(
        id: String,
    ): String =
        Jwts
            .builder()
            .subject(id)
            .claim("type", ACCESS_KEY)
            .signWith(secretKey)
            .issuedAt(Date()) // 발행 시간 설정
            .expiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000))
            .compact()

    private fun generateRefreshToken(
    ): String =
        Jwts
            .builder()
            .claim("type", REFRESH_KEY)
            .signWith(secretKey)
            .issuedAt(Date()) // 발행 시간 설정
            .expiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000))
            .compact()

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader(jwtProperties.header) ?: return null

        return if (bearer.startsWith("Bearer ", ignoreCase = true)) {
            bearer.substring(7).trim()
        } else {
            bearer.trim()
        }
    }

    fun authentication(token: String): Authentication? {
        val userDetails: UserDetails = getDetails(getJws(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getJws(token: String): Claims {
        println(token)

        return try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw ExpiredJwtException
        } catch (e: Exception) {
            throw InvalidJwtException
        }
    }

    private fun getDetails(body: Claims): UserDetails = authDetailsService.loadUserByUsername(body.subject)

}
