package com.xquare.v1servicegit.git.port

import com.xquare.v1servicegit.git.Git
import java.util.*

interface QueryGitPort {
    suspend fun getAllGit(): List<Git>
    suspend fun getAllGitByContributionCount(): List<Git>
    suspend fun getGitByUserId(userId: UUID): Git?
    suspend fun getGitByUsername(username: String): Git?
    suspend fun getContributionCount(username: String): Int
    suspend fun isExistGitUserByUserId(userId: UUID): Boolean
}
