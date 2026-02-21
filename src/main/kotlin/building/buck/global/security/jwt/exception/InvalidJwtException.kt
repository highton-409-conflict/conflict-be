package building.buck.global.security.jwt.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object InvalidJwtException : DuckException(
    ErrorCode.INVALID_TOKEN
)
