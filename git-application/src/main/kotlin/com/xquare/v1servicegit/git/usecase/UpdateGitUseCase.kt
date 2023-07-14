package com.xquare.v1servicegit.git.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.git.exceptions.GitExceptions
import com.xquare.v1servicegit.git.port.CommandGitPort
import com.xquare.v1servicegit.git.port.QueryGitPort

@UseCase
class UpdateGitUseCase(
    private val queryGitPort: QueryGitPort,
    private val commandGitPort: CommandGitPort
) {
    suspend fun execute() {
        val gitAllInfo = queryGitPort.getAllGitByContributionCount()
        val updateContributionMap = commandGitPort.updateContributionCount(gitAllInfo)

        gitAllInfo.forEachIndexed { index, gitInfo ->
            val updateContribution = updateContributionMap[gitInfo.userId] ?: throw GitExceptions.NotFound()
            val updatedGitInfo = gitInfo.updateGit(updateContribution, index + 1)
            commandGitPort.updateGit(updatedGitInfo)
        }
    }
}
