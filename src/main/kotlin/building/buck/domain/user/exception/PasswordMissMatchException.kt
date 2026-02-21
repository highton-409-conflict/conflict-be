package com.example.debatematch.domain.user.exception

import building.buck.global.error.exception.DuckException
import building.buck.global.error.exception.ErrorCode

object PasswordMissMatchException : DuckException(
    ErrorCode.PASSWORD_MISS_MATCH
)
