package building.buck.domain.timeline.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object NotTimelineOwnerException : DuckException(ErrorCode.NOT_TIMELINE_OWNER)
