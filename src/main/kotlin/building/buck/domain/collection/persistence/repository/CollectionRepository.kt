package building.buck.domain.collection.persistence.repository

import building.buck.domain.collection.persistence.Collection
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CollectionRepository : JpaRepository<Collection, UUID> {
    fun findAllByUserId(userId: UUID): List<Collection>?
    fun findByUserIdAndDuckLessThan(userId: UUID, duck: Int): Collection?
}
