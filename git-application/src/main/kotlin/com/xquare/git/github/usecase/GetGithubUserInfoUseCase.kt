package com.xquare.git.github.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.github.spi.QueryGithubPort

@UseCase
class GetGithubUserInfoUseCase(
    private val queryGithubPort: QueryGithubPort,
) {
    suspend fun execute(token: String): String {
        return queryGithubPort.getGithubUserInfo(token).login
            ?: throw GitExceptions.NotFound()
    }
}
