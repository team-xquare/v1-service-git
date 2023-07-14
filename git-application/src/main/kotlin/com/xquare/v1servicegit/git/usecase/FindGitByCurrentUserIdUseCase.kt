package com.xquare.v1servicegit.git.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse.FindUserElement
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.user.port.QueryUserPort
import java.util.UUID

@UseCase
class FindGitByCurrentUserIdUseCase(
    private val queryGitPort: QueryGitPort,
    private val queryUserPort: QueryUserPort,
) {
    suspend fun execute(userId: UUID): FindUserElement {
        val gitUserInfo = queryGitPort.getGitByUserId(userId) ?: throw GitExceptions.NotFound()
        val nameAndProfileFileName = queryUserPort.getNameAndProfileFileName(userId)

        return FindUserElement(
            userId = gitUserInfo.userId,
            name = nameAndProfileFileName.name,
            username = gitUserInfo.username,
            profileFileName = nameAndProfileFileName.profileFileName,
            contributions = gitUserInfo.contributions,
            ranking = gitUserInfo.ranking,
        )
    }
}
