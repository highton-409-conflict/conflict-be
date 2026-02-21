package building.buck.domain.user.service

import building.buck.global.security.jwt.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val jwtTokenProvider: JwtTokenProvider
){
    @Transactional
    fun execute(token: String) =
        jwtTokenProvider.reIssue(token)
}
