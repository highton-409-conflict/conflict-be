package building.buck.domain.user.service

import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.exception.UserNotFoundException
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.req.LoginReq
import building.buck.global.security.jwt.JwtTokenProvider
import building.buck.global.security.jwt.response.TokenResponse
import com.example.debatematch.domain.user.exception.PasswordMissMatchException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userFacade: UserFacade,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun execute(req: LoginReq): TokenResponse {
        val user = check(req)

        return jwtTokenProvider.generateToken(user.accountId)
    }
    private fun check(req: LoginReq): User{
        if(! userRepository.existsByAccountId(req.accountId)){
            throw UserNotFoundException
        }
        val user = userFacade.getUserByAccountIdOrThrow(req.accountId)

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw PasswordMissMatchException
        }

        return user
    }
}