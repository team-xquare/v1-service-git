package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.dto.FindAllUserResponse
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.spi.QueryGitPort
import com.xquare.git.user.spi.QueryUserPort

@UseCase
class FindAllGitUseCase(
    private val queryGitPort: QueryGitPort,
    private val queryUserPort: QueryUserPort
) {
    suspend fun execute(): FindAllUserResponse {
        val response = queryGitPort.getAllGit()
            .map {
                val name = queryUserPort.getName(it.userId).name
                FindUserElement(
                    userId = it.userId,
                    name = name,
                    username = it.username,
                    avatarUrl = it.avatarUrl,
                    contributions = it.contributions
                )
            }

        return FindAllUserResponse(response)
    }
}