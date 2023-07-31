package com.xquare.git.git.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.git.git.createGit
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.git.usecase.GitUseCase
import com.xquare.v1servicegit.github.port.QueryGithubPort
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse.FindUserInfoElement
import com.xquare.v1servicegit.user.port.QueryUserPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class FindGitByCurrentUserIdTest : BaseApplicationTest({
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

    describe("자신의 깃허브 정보를 불러올 때") {
        val name = "testName"
        val profileFileName = "testProfileFileName"

        val userInfo = createGit()

        context("탐색하는 깃허브 정보가 존재하지 않으면") {
            coEvery { queryGitPort.getGitByUserId(userInfo.userId) } returns null

            it("예외를 던진다.") {
                shouldThrow<GitExceptions.NotFound> {
                    gitUseCase.getMyGithubInfo(userInfo.userId)
                }
            }
        }

        coEvery { queryGitPort.getGitByUserId(userInfo.userId) } returns userInfo
        coEvery { queryUserPort.getNameAndProfileFileName(userInfo.userId) } returns FindUserInfoElement(
            id = userInfo.userId,
            name = name,
            profileFileName = profileFileName,
        )

        it("Response를 반환한다.") {
            val result = gitUseCase.getMyGithubInfo(userInfo.userId)

            result shouldBe FindAllUserResponse.FindUserElement(
                userId = userInfo.userId,
                name = name,
                username = userInfo.username,
                profileFileName = profileFileName,
                contributions = userInfo.contributions,
                ranking = userInfo.ranking,
            )
        }
    }
})
