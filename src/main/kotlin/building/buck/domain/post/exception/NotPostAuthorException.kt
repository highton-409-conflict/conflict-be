package building.buck.domain.post.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object NotPostAuthorException : DuckException(ErrorCode.NOT_POST_AUTHOR)
