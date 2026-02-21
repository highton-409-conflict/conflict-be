package building.buck.domain.post.presentation.dto.req

import java.util.UUID

data class CreatePostReq(
    val title: String,
    val content: String,
    val tags: List<UUID>?
)
