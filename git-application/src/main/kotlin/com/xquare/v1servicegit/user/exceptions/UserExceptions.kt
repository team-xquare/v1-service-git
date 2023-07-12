package com.xquare.v1servicegit.user.exceptions

import com.xquare.v1servicegit.common.exceptions.BaseException

sealed class UserExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class UnAuthorized(message: String = UN_AUTHORIZED) : UserExceptions(401, message)

    companion object {
        private const val UN_AUTHORIZED = "Un Authorized"
    }
}
