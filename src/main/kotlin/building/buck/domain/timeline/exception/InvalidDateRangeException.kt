package building.buck.domain.timeline.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object InvalidDateRangeException : DuckException(ErrorCode.INVALID_DATE_RANGE)
