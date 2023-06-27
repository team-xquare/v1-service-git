package com.xquare.git.git.spi

import com.xquare.git.git.model.Git
import java.util.*

interface CommandGitPort {
    suspend fun saveUser(git: Git)
    suspend fun updateGit(git: Git)
    suspend fun updateContributionCount(gitAllInfo: List<Git>): Map<UUID, Int>
}
