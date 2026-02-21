package building.buck.domain.collection.persistence.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class CollectionCustomRepositoryImpl : CollectionCustomRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    override fun incrementDuck(collectionId: UUID): Int {
        val query = entityManager.createNativeQuery(
            "UPDATE tbl_collection SET duck = duck + 1 WHERE id = :id"
        )
        query.setParameter("id", collectionId)
        return query.executeUpdate()
    }

    @Transactional
    override fun resetDuck(collectionId: UUID): Int {
        val query = entityManager.createNativeQuery(
            "UPDATE tbl_collection SET duck = 0 WHERE id = :id"
        )
        query.setParameter("id", collectionId)
        return query.executeUpdate()
    }
}
