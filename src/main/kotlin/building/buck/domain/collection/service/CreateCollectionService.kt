package building.buck.domain.collection.service

import building.buck.domain.collection.persistence.Collection
import building.buck.domain.collection.persistence.repository.CollectionRepository
import building.buck.domain.collection.presentation.dto.req.CreateCollectionReq
import building.buck.domain.collection.presentation.dto.res.CollectionRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateCollectionService(
    private val collectionRepository: CollectionRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(req: CreateCollectionReq): CollectionRes {
        val user = userFacade.currentUser()

        // Check for in-progress collection (duck < 7) and delete it
        val inProgress = collectionRepository.findByUserIdAndDuckLessThan(user.id!!, 7)
        inProgress?.let {
            collectionRepository.delete(it)
        }

        val collection = collectionRepository.save(
            Collection(
                duck = 0,
                image = req.image,
                user = user
            )
        )

        return CollectionRes(
            id = collection.id!!,
            duck = 0,
            image = collection.image
        )
    }
}
