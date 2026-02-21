package building.buck.domain.timeline.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object TimelineItemNotFoundException : DuckException(ErrorCode.TIMELINE_ITEM_NOT_FOUND)
