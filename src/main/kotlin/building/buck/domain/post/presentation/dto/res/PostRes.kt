package building.buck.domain.post.presentation.dto.res

import java.time.LocalDateTime
import java.util.UUID

data class PostRes(
    val id: UUID,
    val title: String,
    val content: String,
    val authorId: UUID,
    val createdAt: LocalDateTime,
    val likes: Long,
    val isLiked: Boolean,
    val tags: List<TagRes>,
)
data class TagRes(
    val tagId: UUID,
    val tagName: String,
)
