package building.buck.domain.collection.service

import building.buck.domain.collection.persistence.repository.CollectionRepository
import building.buck.domain.collection.presentation.dto.res.CollectionRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service

@Service
class GetMyCollectionsService(
    private val userFacade: UserFacade,
    private val collectionRepository: CollectionRepository
) {
    fun execute(): List<CollectionRes>? {
        val user = userFacade.currentUser()

        return collectionRepository.findAllByUserId(user.id!!)
            ?.map {
                CollectionRes(
                    id = it.id!!,
                    duck = it.duck,
                    image = it.image
                )
            }
    }
}
