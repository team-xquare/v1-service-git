package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.dto.FindAllUserResponse
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.dto.FindUserInfoRequest
import com.xquare.git.git.spi.QueryGitPort
import com.xquare.git.user.spi.QueryUserPort

@UseCase
class FindAllGitUseCase(
    private val queryGitPort: QueryGitPort,
    private val queryUserPort: QueryUserPort,
) {
    suspend fun execute(): FindAllUserResponse {
        val gitInfoList = queryGitPort.getAllGit()
        val userIds = gitInfoList.map { it.userId }
        val userInfoRequest = FindUserInfoRequest(userIds)
        val gitUserInfoList = queryUserPort.getAllUserInfo(userInfoRequest).users

        val response = gitInfoList.map { gitInfo ->
            val userInfo = gitUserInfoList.single { git -> git.id == gitInfo.userId }
            FindUserElement(
                userId = gitInfo.userId,
                name = userInfo.name,
                username = gitInfo.username,
                profileFileName = userInfo.profileFileName,
                contributions = gitInfo.contributions,
            )
        }
        return FindAllUserResponse(response)
    }
}