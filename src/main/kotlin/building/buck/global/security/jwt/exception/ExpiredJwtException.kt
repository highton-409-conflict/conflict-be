package building.buck.global.security.jwt.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object ExpiredJwtException : DuckException(
    ErrorCode.EXPIRED_TOKEN
)
