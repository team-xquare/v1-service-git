package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.git.spi.QueryGitPort
import com.xquare.git.user.spi.QueryUserPort
import java.util.*

@UseCase
class FindGitByCurrentUserIdUseCase(
    private val queryGitPort: QueryGitPort,
    private val queryUserPort: QueryUserPort
) {
    suspend fun execute(userId: UUID): FindUserElement {
        val gitUserInfo = queryGitPort.getGitByUserId(userId) ?: throw GitExceptions.NotFound()
        val nameAndProfileFileName = queryUserPort.getNameAndProfileFileName(userId)
        
        return FindUserElement(
            userId = gitUserInfo.userId,
            name = nameAndProfileFileName.name,
            username = gitUserInfo.username,
            profileFileName = nameAndProfileFileName.profileFileName,
            contributions = gitUserInfo.contributions
        )
    }
}