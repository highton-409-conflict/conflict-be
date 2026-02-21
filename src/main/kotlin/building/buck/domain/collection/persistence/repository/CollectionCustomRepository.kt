package building.buck.domain.collection.persistence.repository

import java.util.UUID

interface CollectionCustomRepository {
    fun incrementDuck(collectionId: UUID): Int
    fun resetDuck(collectionId: UUID): Int
}
