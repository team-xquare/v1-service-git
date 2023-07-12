package com.xquare.v1servicegit.git.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.Git
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort
import java.util.UUID

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
