package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.spi.CommandGitPort
import com.xquare.git.git.spi.QueryGitPort

@UseCase
class UpdateGit(
    private val queryGitPort: QueryGitPort,
    private val commandGitPort: CommandGitPort
) {
    suspend fun execute() {
        queryGitPort.getAllGit()
            .map {
                val updateContribution = queryGitPort.getContributionCount(it.username)
                val updateAvatarUrl = queryGitPort.getAvatarUrl(it.username).avatarUrl
                val updateGit = it.updateGit(updateAvatarUrl, updateContribution)
                commandGitPort.updateGit(updateGit)
            }
    }
}