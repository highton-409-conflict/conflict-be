package building.buck.domain.post.presentation.dto.req

import java.util.*

data class UpdatePostReq(
    val title: String,
    val content: String,
    val tags: List<UUID>
)
