package building.buck.domain.topic.presentation.dto.req

import java.util.UUID

data class CreateTopicReq(
    val name: String,
    val parentId: UUID?,
    val image: String?
)
