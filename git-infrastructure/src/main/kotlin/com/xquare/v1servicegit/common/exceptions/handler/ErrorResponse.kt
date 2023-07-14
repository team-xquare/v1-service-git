package com.xquare.v1servicegit.common.exceptions.handler

import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class ErrorResponse(
    val errorStatus: Int,
    val errorMessage: Any,
)

fun BindingResult.of(): ErrorResponse {
    val errorMap = HashMap<String, String?>()

    for (error: FieldError in this.fieldErrors) errorMap[error.field] = error.defaultMessage

    return ErrorResponse(
        errorStatus = 500,
        errorMessage = errorMap,
    )
}
