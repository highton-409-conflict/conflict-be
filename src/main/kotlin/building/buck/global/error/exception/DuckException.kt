package building.buck.global.error.exception

abstract class DuckException(
    val errorCode: ErrorCode
) : RuntimeException()
