package com.xquare.v1servicegit.git.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.Git
import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.dto.response.toUserElement
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.github.port.QueryGithubPort
import com.xquare.v1servicegit.user.port.QueryUserPort
import java.util.UUID

@UseCase
class GitUseCase(
    private val commandGitPort: CommandGitPort,
    private val queryGitPort: QueryGitPort,
    private val queryGithubPort: QueryGithubPort,
    private val queryUserPort: QueryUserPort,
) {

    suspend fun getAllGithubInfo(): FindAllUserResponse {
        val gitInfoList = queryGitPort.getAllGit()
        val userIds = gitInfoList.map { it.userId }
        val userInfoRequest = FindUserInfoRequest(userIds)
        val gitUserInfoList = queryUserPort.getAllUserInfo(userInfoRequest).users

        val response = gitInfoList.map { gitInfo ->
            val userInfo = gitUserInfoList.find { git -> git.id == gitInfo.userId }
                ?: throw GitExceptions.NotFound()
            gitInfo.toUserElement(userInfo)
        }
        return FindAllUserResponse(response)
    }

    suspend fun getMyGithubInfo(currentUserId: UUID): FindAllUserResponse.FindUserElement {
        val gitUser = queryGitPort.getGitByUserId(currentUserId)
            ?: throw GitExceptions.NotFound()
        val userInfo = queryUserPort.getNameAndProfileFileName(currentUserId)

        return gitUser.toUserElement(userInfo)
    }

    suspend fun saveGithubUserInfo(currentUserId: UUID, githubCode: String) {
        val githubAccessToken = queryGithubPort.getAccessToken(githubCode).accessToken
            ?: throw GitExceptions.UnauthorizedToken()
        val githubUserName = queryGithubPort.getGithubUserInfo(githubAccessToken).login
            ?: throw GitExceptions.NotFound()
        val contributions = queryGitPort.getContributionCount(githubUserName)

        commandGitPort.saveUser(
            Git(
                userId = currentUserId,
                username = githubUserName,
                contributions = contributions,
                ranking = 0,
            ),
        )
    }

    suspend fun updateGitInfo() {
        val gitAllInfo = queryGitPort.getAllGitByContributionCount()
        val updateContributionMap = commandGitPort.updateContributionCount(gitAllInfo)

        gitAllInfo.forEachIndexed { index, gitInfo ->
            val updateContribution = updateContributionMap[gitInfo.userId]
                ?: throw GitExceptions.NotFound()
            commandGitPort.updateGit(
                git = gitInfo.updateGit(updateContribution, index + 1),
            )
        }
    }
}
