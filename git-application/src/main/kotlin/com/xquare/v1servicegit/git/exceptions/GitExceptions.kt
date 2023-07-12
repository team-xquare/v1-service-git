package com.xquare.v1servicegit.git.exceptions

import com.xquare.v1servicegit.common.exceptions.BaseException

sealed class GitExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class UnauthorizedToken(message: String = UNAUTHORIZED_TOKEN) : GitExceptions(401, message)

    class NotFound(message: String = NOT_FOUND) : GitExceptions(404, message)

    class AlreadyExists(message: String = ALREADY_EXISTS) : GitExceptions(409, message)

    companion object {
        private const val UNAUTHORIZED_TOKEN = "Unauthorized Token"
        private const val NOT_FOUND = "Not Found"
        private const val ALREADY_EXISTS = "Already Exists"
    }
}
