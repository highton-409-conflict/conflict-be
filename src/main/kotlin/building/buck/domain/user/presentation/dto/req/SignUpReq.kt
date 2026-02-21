package building.buck.domain.user.presentation.dto.req

data class SignUpReq(
    val accountId: String,
    val password: String,
    val name: String
)
