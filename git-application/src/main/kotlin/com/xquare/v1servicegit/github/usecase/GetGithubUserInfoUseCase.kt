package com.xquare.v1servicegit.github.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.github.port.QueryGithubPort

@UseCase
class GetGithubUserInfoUseCase(
    private val queryGithubPort: QueryGithubPort,
) {
    suspend fun execute(token: String): String {
        return queryGithubPort.getGithubUserInfo(token).login
            ?: throw GitExceptions.NotFound()
    }
}
