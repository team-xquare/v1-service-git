package com.xquare.v1servicegit.git.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse.FindUserElement
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.user.port.QueryUserPort

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
                ranking = gitInfo.ranking,
            )
        }
        return FindAllUserResponse(response)
    }
}
