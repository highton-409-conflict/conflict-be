package building.buck.domain.collection.service

import building.buck.domain.collection.exception.CollectionNotFoundException
import building.buck.domain.collection.persistence.SelectCollection
import building.buck.domain.collection.persistence.repository.CollectionRepository
import building.buck.domain.collection.persistence.repository.SelectCollectionRepository
import building.buck.domain.collection.presentation.dto.req.SelectCollectionReq
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SelectCollectionService(
    private val userFacade: UserFacade,
    private val selectCollectionRepository: SelectCollectionRepository,
    private val collectionRepository: CollectionRepository
) {
    @Transactional
    fun execute(req: SelectCollectionReq) {
        val user = userFacade.currentUser()

        selectCollectionRepository.deleteAllByUserId(user.id!!)

        val collection = collectionRepository.findById(req.collectionId)
            .orElseThrow { CollectionNotFoundException }

        // 자신의 컬렉션인지 확인
        if (collection.user.id != user.id) {
            throw CollectionNotFoundException
        }

        selectCollectionRepository.save(SelectCollection(
            collection = collection,
            user = user
        ))
    }
}