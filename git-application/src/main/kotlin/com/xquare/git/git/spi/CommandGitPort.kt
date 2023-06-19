package com.xquare.git.git.spi

import com.xquare.git.git.model.Git

interface CommandGitPort {
    suspend fun saveUser(git: Git)
    suspend fun updateGit(git: Git)
}
