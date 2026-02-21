package building.buck.domain.collection.persistence.repository

import building.buck.domain.collection.persistence.SelectCollection
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SelectCollectionRepository: JpaRepository<SelectCollection, UUID> {
    fun findByUserId(userId: UUID): SelectCollection?
    fun deleteAllByUserId(userId: UUID)
}