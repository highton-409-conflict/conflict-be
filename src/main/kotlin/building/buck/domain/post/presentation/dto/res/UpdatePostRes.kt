package building.buck.domain.post.presentation.dto.res

import java.time.LocalDateTime
import java.util.*

data class UpdatePostRes (
    val postId: UUID,
    val title: String,
    val content: String,
    val authorId: UUID,
    val createdAt: LocalDateTime,
)