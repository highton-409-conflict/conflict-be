package building.buck.global.security.jwt.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
