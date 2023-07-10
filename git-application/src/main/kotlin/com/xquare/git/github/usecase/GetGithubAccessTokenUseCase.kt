package com.xquare.git.github.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.github.spi.QueryGithubPort

@UseCase
class GetGithubAccessTokenUseCase(
    private val queryGithubPort: QueryGithubPort
) {
    suspend fun execute(code: String): String {
        return queryGithubPort.getAccessToken(code).accessToken
            ?: throw GitExceptions.UnauthorizedToken()
    }
}