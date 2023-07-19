package com.xquare.git.git.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.git.git.createGit
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.git.usecase.UpdateGitUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coEvery
import io.mockk.mockk

class UpdateGitTest : BaseApplicationTest({
    val queryGitPort: QueryGitPort = mockk()
    val commandGitPort: CommandGitPort = mockk()

    val updateGitUseCase = UpdateGitUseCase(
        queryGitPort = queryGitPort,
        commandGitPort = commandGitPort,
    )

    describe("모든 깃허브 정보를 불러올 때") {
        val ranking = 1
        val userInfo = createGit()
        val userList = listOf(userInfo)

        context("탐색하는 깃허브 정보가 존재하지 않으면") {
            coEvery { queryGitPort.getAllGitByContributionCount() } returns userList
            coEvery { commandGitPort.updateContributionCount(userList) } returns emptyMap()
            coEvery { commandGitPort.updateGit(userInfo) } returns Unit

            it("예외를 던진다.") {
                shouldThrow<GitExceptions.NotFound> {
                    updateGitUseCase.execute()
                }
            }
        }

        coEvery { queryGitPort.getAllGitByContributionCount() } returns userList
        coEvery { commandGitPort.updateContributionCount(userList) } returns mapOf(userInfo.userId to ranking)
        coEvery { commandGitPort.updateGit(userInfo) } returns Unit

        it("값을 업데이트한다.") {
            updateGitUseCase.execute()
        }
    }
})