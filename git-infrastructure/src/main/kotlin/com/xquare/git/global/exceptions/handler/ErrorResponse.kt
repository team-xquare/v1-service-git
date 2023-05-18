package com.xquare.git.global.exceptions.handler

import com.xquare.git.exceptions.BaseException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class ErrorResponse(
    val errorStatus: Int,
    val errorMessage: Any
) {
    companion object {
        fun of(exception: BaseException) = ErrorResponse(
            exception.statusCode,
            exception.errorMessage
        )

        fun of(e: BindingResult): ErrorResponse {
            val errorMap = HashMap<String, String?>()

            for (error: FieldError in e.fieldErrors) errorMap[error.field] = error.defaultMessage

            return ErrorResponse(
                errorStatus = 500,
                errorMessage = errorMap
            )
        }
    }
}
