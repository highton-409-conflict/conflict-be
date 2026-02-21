package building.buck.domain.user.presentation.dto.req

data class LoginReq(
    val accountId: String,
    val password: String
)
