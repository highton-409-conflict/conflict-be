package building.buck.domain.collection.presentation.dto.res

import java.util.UUID

data class CollectionRes(
    val id: UUID,
    val duck: Int,
    val image: String?
)
