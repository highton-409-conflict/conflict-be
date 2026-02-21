package building.buck.domain.topic.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object TopicNotFoundException : DuckException(ErrorCode.TOPIC_NOT_FOUND)
