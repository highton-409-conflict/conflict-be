package building.buck.domain.user.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object CannotFollowSelfException : DuckException(ErrorCode.CANNOT_FOLLOW_SELF)
