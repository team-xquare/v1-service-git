package com.xquare.v1servicegit.github.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.github.port.QueryGithubPort

@UseCase
class GetGithubAccessTokenUseCase(
    private val queryGithubPort: QueryGithubPort,
) {
    suspend fun execute(code: String): String {
        return queryGithubPort.getAccessToken(code).accessToken
            ?: throw GitExceptions.UnauthorizedToken()
    }
}
