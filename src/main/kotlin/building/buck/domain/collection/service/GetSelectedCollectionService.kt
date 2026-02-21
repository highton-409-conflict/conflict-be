package building.buck.domain.collection.service

import building.buck.domain.collection.persistence.repository.SelectCollectionRepository
import building.buck.domain.collection.presentation.dto.res.CollectionRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class GetSelectedCollectionService(
    private val userFacade: UserFacade,
    private val selectCollectionRepository: SelectCollectionRepository
) {
    fun execute(): CollectionRes? {
        val user = userFacade.currentUser()

        val selectCollection = selectCollectionRepository.findByUserId(user.id!!)

        return selectCollection?.let {  CollectionRes(
            id = selectCollection.collection.id!!,
            duck = selectCollection.collection.duck,
            image = selectCollection.collection.image
        ) }
    }
}
