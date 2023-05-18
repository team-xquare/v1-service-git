package com.xquare.git.git.exceptions

import com.xquare.git.exceptions.BaseException

sealed class GitExceptions(
    override val statusCode: Int,
    override val errorMessage: String
) : BaseException(statusCode, errorMessage) {

    class AlreadyExists(message: String = ALREADY_EXISTS) : GitExceptions(409, message)

    companion object {
        private const val ALREADY_EXISTS = "Already Exists"
    }
}