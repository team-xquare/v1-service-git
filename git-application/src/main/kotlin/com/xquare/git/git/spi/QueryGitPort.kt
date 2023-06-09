package com.xquare.git.git.spi

import com.xquare.git.git.model.Git
import java.util.*

interface QueryGitPort {
    suspend fun getAllGit(): List<Git>
    suspend fun getAllGitByContributionCount(): List<Git>
    suspend fun getGitByUserId(userId: UUID): Git?
    suspend fun getGitByUsername(username: String): Git?
    suspend fun getContributionCount(username: String): Int
}