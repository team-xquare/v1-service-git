package com.xquare.git.git.exceptions

import com.xquare.git.exceptions.BaseException

sealed class GitExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class NotFound(message: String = NOT_FOUND) : GitExceptions(404, message)

    class AlreadyExists(message: String = ALREADY_EXISTS) : GitExceptions(409, message)

    class UnauthorizedToken(message: String = UNAUTHORIZED_TOKEN) : GitExceptions(401, message)

    companion object {
        private const val NOT_FOUND = "Not Found"
        private const val ALREADY_EXISTS = "Already Exists"
        private const val UNAUTHORIZED_TOKEN = "Unauthorized Token"
    }
}