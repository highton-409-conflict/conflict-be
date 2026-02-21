package building.buck.domain.topic.presentation.dto.res

import java.util.UUID

data class TopicRes(
    val id: UUID,
    val name: String,
    val parentId: UUID?,
    val image: String?
)
