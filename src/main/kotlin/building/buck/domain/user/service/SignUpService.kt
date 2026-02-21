package building.buck.domain.user.service

import building.buck.domain.timeline.persistence.Timeline
import building.buck.domain.timeline.persistence.repository.TimelineRepository
import building.buck.domain.user.exception.UserDuplicationException
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.req.SignUpReq
import building.buck.global.security.jwt.JwtTokenProvider
import building.buck.global.security.jwt.response.TokenResponse
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val timelineRepository: TimelineRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun execute(req: SignUpReq): TokenResponse {
        checkDuplicateAccountId(req.accountId)

        val user = userRepository.save(
            User(
                accountId = req.accountId,
                password = passwordEncoder.encode(req.password),
                name = req.name,
            )
        )
        val timeline = timelineRepository.save(
            Timeline(
                user = user,
            )
        )
        // 연관관계의 소유자(User 쪽)에 FK가 있는 매핑이면, 이 값을 DB에 반영하려면 User도 업데이트가 필요합니다.
        user.timeline = timeline
        userRepository.save(user)

        return jwtTokenProvider.generateToken(user.accountId)

    }

    private fun checkDuplicateAccountId(accountId: String) {
        if (userRepository.existsByAccountId(accountId))
            throw UserDuplicationException
    }
}