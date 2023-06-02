package com.xquare.git.global.exceptions

import com.xquare.git.exceptions.BaseException

sealed class FeignExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class BadRequest(message: String = BAD_REQUEST) : FeignExceptions(400, message)

    class UnAuthorized(message: String = UN_AUTHORIZED) : FeignExceptions(401, message)

    class Forbidden(message: String = FORBIDDEN) : FeignExceptions(403, message)

    class ExpiredToken(message: String = EXPIRED_TOKEN) : FeignExceptions(419, message)

    class InternalServerError(message: String = UNEXPECTED_EXCEPTION) : FeignExceptions(500, message)


    companion object {
        const val BAD_REQUEST = "Feign BadRequest"
        const val UN_AUTHORIZED = "Feign Unauthorized"
        const val FORBIDDEN = "Feign Forbidden"
        const val EXPIRED_TOKEN = "Feign Expired Token"
        const val UNEXPECTED_EXCEPTION = "Feign Unexpected Error Occurred"
    }
}