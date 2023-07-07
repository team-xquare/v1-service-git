package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.model.Git
import com.xquare.git.git.spi.CommandGitPort
import com.xquare.git.git.spi.QueryGitPort
import java.util.*

@UseCase
class SaveUsernameUseCase(
    private val commandGitPort: CommandGitPort,
    private val queryGitPort: QueryGitPort,
) {
    suspend fun execute(currentUserId: UUID, username: String) {
        val contributions = queryGitPort.getContributionCount(username)

        commandGitPort.saveUser(
            Git(
                userId = currentUserId,
                username = username,
                contributions = contributions,
                ranking = 0,
            )
        )
    }
}
