package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.git.spi.CommandGitPort
import com.xquare.git.git.spi.QueryGitPort

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