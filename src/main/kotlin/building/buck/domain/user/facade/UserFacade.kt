package building.buck.domain.user.facade

import building.buck.domain.user.exception.UserNotFoundException
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
) {
    fun currentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByAccountId(accountId) ?: throw UserNotFoundException
    }

    fun currentUserOrThrow(): User? {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByAccountId(accountId)
    }

    fun getUserByAccountIdOrThrow(accountId: String): User {
        return userRepository.findByAccountId(accountId) ?: throw UserNotFoundException
    }
}
