package building.buck.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    private val excludedPaths = listOf("/auth/signup","/auth/login")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        if (excludedPaths.any { path.endsWith(it) }) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtTokenProvider.resolveToken(request)

        token?.run {
            SecurityContextHolder.getContext().authentication = jwtTokenProvider.authentication(token)
        }

        filterChain.doFilter(request, response)
    }
}
