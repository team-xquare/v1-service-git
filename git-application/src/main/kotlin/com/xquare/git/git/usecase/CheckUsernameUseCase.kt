package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.git.spi.QueryGitPort

@UseCase
class CheckUsernameUseCase(
    private val queryGitPort: QueryGitPort
) {
    suspend fun execute(username: String) {
        if (queryGitPort.getGitByUsername(username) != null) {
            throw GitExceptions.AlreadyExists()
        }
    }
}