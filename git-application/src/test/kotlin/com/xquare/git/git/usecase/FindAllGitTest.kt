package com.xquare.git.git.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.git.git.createGit
import com.xquare.v1servicegit.git.Git
import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse.FindUserElement
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.git.usecase.GitUseCase
import com.xquare.v1servicegit.github.port.QueryGithubPort
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse.FindUserInfoElement
import com.xquare.v1servicegit.user.port.QueryUserPort
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class FindAllGitTest : BaseApplicationTest({
    val commandGitPort: CommandGitPort = mockk()
    val queryGitPort: QueryGitPort = mockk()
    val queryGithubPort: QueryGithubPort = mockk()
    val queryUserPort: QueryUserPort = mockk()

    val gitUseCase = GitUseCase(
        commandGitPort = commandGitPort,
        queryGitPort = queryGitPort,
        queryGithubPort = queryGithubPort,
        queryUserPort = queryUserPort,
    )

    describe("모든 깃허브 정보를 불러올 때") {
        val name = "testName"
        val profileFileName = "testProfileFileName"
        val userInfo = createGit()
        val userIds = listOf(userInfo).map(Git::userId)
        val request = FindUserInfoRequest(userIds)

        coEvery { queryGitPort.getAllGit() } returns listOf(userInfo)
        coEvery { queryUserPort.getAllUserInfo(request) } returns FindUserListResponse(
            listOf(
                FindUserInfoElement(
                    id = userInfo.userId,
                    name = name,
                    profileFileName = profileFileName,
                ),
            ),
        )

        it("Response를 반환한다.") {
            val result = gitUseCase.getAllGithubInfo()

            result shouldBe FindAllUserResponse(
                listOf(
                    FindUserElement(
                        userId = userInfo.userId,
                        name = name,
                        username = userInfo.username,
                        profileFileName = profileFileName,
                        contributions = userInfo.contributions,
                        ranking = userInfo.ranking,
                    ),
                ),
            )
        }
    }
})
