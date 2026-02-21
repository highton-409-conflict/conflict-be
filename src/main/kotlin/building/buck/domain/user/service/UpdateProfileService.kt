package building.buck.domain.user.service

import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.presentation.dto.req.UpdateProfileReq
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UpdateProfileService(
    private val userFacade: UserFacade,
) {
    @Transactional
    fun execute(req: UpdateProfileReq) {
        val user = userFacade.currentUser()

        user.profile = req.image
        user.introduce = req.introduce
        req.name?.let { user.name = req.name }
    }
}
