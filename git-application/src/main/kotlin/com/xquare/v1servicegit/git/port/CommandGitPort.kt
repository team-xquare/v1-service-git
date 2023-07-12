package com.xquare.v1servicegit.git.port

import com.xquare.v1servicegit.git.Git
import java.util.*

interface CommandGitPort {
    suspend fun saveUser(git: Git)
    suspend fun updateGit(git: Git)
    suspend fun updateContributionCount(gitAllInfo: List<Git>): Map<UUID, Int>
}
