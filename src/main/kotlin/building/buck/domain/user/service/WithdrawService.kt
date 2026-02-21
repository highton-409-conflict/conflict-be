package building.buck.domain.user.service

import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawService(
    private val userRepository: UserRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute() {
        val user = userFacade.currentUser()
        userRepository.delete(user) // Cascade handles related data
    }
}
