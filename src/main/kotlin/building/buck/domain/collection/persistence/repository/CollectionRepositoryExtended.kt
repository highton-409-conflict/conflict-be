package building.buck.domain.collection.persistence.repository

import building.buck.domain.collection.persistence.Collection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CollectionRepositoryExtended : JpaRepository<Collection, UUID>{
    fun findAllByUserId(userId: UUID): List<Collection>
}
