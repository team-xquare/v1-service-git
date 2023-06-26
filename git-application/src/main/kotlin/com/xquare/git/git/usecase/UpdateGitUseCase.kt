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
        val gitAllInfo = queryGitPort.getAllGit()
        val updateContributionMap = commandGitPort.updateContributionCount(gitAllInfo)

        gitAllInfo.forEach { git ->
            val updateContribution = updateContributionMap[git.userId] ?: throw GitExceptions.NotFound()
            val updateGit = git.updateGit(updateContribution)
            commandGitPort.updateGit(updateGit)
        }
    }
}