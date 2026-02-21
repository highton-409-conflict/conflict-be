package building.buck.domain.collection.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object CollectionNotFoundException : DuckException(ErrorCode.COLLECTION_NOT_FOUND)
