package building.buck.domain.collection.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object CollectionInProgressException : DuckException(ErrorCode.COLLECTION_IN_PROGRESS)
