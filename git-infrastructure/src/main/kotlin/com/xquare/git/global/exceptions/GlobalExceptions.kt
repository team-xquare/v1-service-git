package com.xquare.git.global.exceptions

import com.xquare.git.exceptions.BaseException

sealed class GlobalExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class BadRequest(message: String = BAD_REQUEST): GlobalExceptions(400, message)
    class RequestHandlerNotFound(message: String=  REQUEST_HANDLER_NOT_FOUND) : GlobalExceptions(404, message)

    class InternalServerError(message: String = UNEXPECTED_EXCEPTION) : GlobalExceptions(500, message)

    companion object {
        const val BAD_REQUEST = "Bad Request"
        const val REQUEST_HANDLER_NOT_FOUND = "Request Handler Not Found"
        const val UNEXPECTED_EXCEPTION = "Unexpected Error Occurred"
    }
}