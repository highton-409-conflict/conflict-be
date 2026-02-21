package building.buck.domain.user.presentation.dto.req

data class UpdateProfileReq(
    val introduce: String?,
    val name: String?,
    val image: String?
)
