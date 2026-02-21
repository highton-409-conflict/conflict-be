package building.buck.domain.user.presentation.dto.res

import java.util.UUID

data class SearchUsersRes(
    val users: List<UserItem>
)

data class UserItem(
    val id: UUID,
    val accountId: String,
    val name: String,
    val profile: String?,
    val followerCount: Int,
    val followingCount: Int,
    val tags: List<TagItem>
)

data class TagItem(
    val id: UUID,
    val name: String
)
