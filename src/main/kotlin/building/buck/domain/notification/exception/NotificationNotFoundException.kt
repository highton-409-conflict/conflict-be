package building.buck.domain.notification.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object NotificationNotFoundException : DuckException(ErrorCode.NOTIFICATION_NOT_FOUND)
