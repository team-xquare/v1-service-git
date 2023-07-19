package com.xquare.git.git.usecase

import com.xquare.git.BaseApplicationTest
import com.xquare.git.git.createGit
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import com.xquare.v1servicegit.git.usecase.SaveUsernameUseCase
import io.mockk.coEvery
import io.mockk.mockk

class SaveUsernameTest : BaseApplicationTest({
    val commandGitPort: CommandGitPort = mockk()
    val queryGitPort: QueryGitPort = mockk()

    val saveUsernameUseCase = SaveUsernameUseCase(
        commandGitPort = commandGitPort,
        queryGitPort = queryGitPort,
    )

    describe("깃허브 유저 이름을 저장할 때") {
        val userInfo = createGit()

        coEvery { queryGitPort.getContributionCount(userInfo.username) } returns userInfo.contributions
        coEvery { commandGitPort.saveUser(userInfo) } returns Unit

        it("유저 이름을 저장한다.") {
            saveUsernameUseCase.execute(userInfo.userId, userInfo.username)
        }
    }
})