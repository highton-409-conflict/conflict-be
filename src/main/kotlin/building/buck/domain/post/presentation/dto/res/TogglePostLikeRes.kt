package building.buck.domain.post.presentation.dto.res

data class TogglePostLikeRes(
    val isLiked: Boolean,
    val likeCount: Long
)
