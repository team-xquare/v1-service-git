package com.xquare.git.github.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.github.port.QueryGithubPort
import com.xquare.v1servicegit.github.usecase.GetGithubUserInfoUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetGithubUserInfoTest : BaseApplicationTest({
    val queryGithubPort: QueryGithubPort = mockk()

    val getGithubUserInfoUseCase = GetGithubUserInfoUseCase(
        queryGithubPort = queryGithubPort,
    )

    describe("깃허브 유저 정보를 가져올 때") {
        val token = "testToken"
        val username = "testUsername"

        context("유저 정보가 없으면") {
            it("예외를 던진다.") {
                coEvery { queryGithubPort.getGithubUserInfo(token).login } returns null

                shouldThrow<GitExceptions.NotFound> {
                    getGithubUserInfoUseCase.execute(token)
                }
            }
        }

        coEvery { queryGithubPort.getGithubUserInfo(token).login } returns username

        it("Response를 반환한다.") {
            val result = getGithubUserInfoUseCase.execute(token)

            result shouldBe username
        }
    }
})