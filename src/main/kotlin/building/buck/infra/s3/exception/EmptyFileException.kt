package dsm.pick2024.infrastructure.s3.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object EmptyFileException : DuckException(
    ErrorCode.FILE_IS_EMPTY
)
