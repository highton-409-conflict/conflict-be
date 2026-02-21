package building.buck.domain.timeline.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object TimelineNotFoundException : DuckException(ErrorCode.TIMELINE_NOT_FOUND)
