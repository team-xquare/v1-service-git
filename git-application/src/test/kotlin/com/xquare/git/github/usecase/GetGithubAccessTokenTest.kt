package com.xquare.git.github.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.github.port.QueryGithubPort
import com.xquare.v1servicegit.github.usecase.GetGithubAccessTokenUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetGithubAccessTokenTest : BaseApplicationTest({
    val queryGithubPort: QueryGithubPort = mockk()

    val getGithubAccessTokenUseCase = GetGithubAccessTokenUseCase(
        queryGithubPort = queryGithubPort,
    )

    describe("깃허브 토큰을 가져올 때") {
        val code = "testCode"
        val accessToken = "testAccessToken"

        context("토큰이 유효하지 않으면") {
            coEvery { queryGithubPort.getAccessToken(code).accessToken } returns null

            it("예외를 던진다.") {
                shouldThrow<GitExceptions.UnauthorizedToken> {
                    getGithubAccessTokenUseCase.execute(code)
                }
            }
        }

        coEvery { queryGithubPort.getAccessToken(code).accessToken } returns accessToken

        it("Response를 반환한다.") {
            val result = getGithubAccessTokenUseCase.execute(code)

            result shouldBe accessToken
        }
    }
})