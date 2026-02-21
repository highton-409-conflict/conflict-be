package building.buck.domain.post.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object PostNotFoundException : DuckException(ErrorCode.POST_NOT_FOUND)
