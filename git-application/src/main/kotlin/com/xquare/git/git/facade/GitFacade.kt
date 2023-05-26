package com.xquare.git.git.facade

import com.xquare.git.git.dto.FindAllUserResponse
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.usecase.CheckUsername
import com.xquare.git.git.usecase.FindAllGit
import com.xquare.git.git.usecase.FindGitByCurrentUserId
import com.xquare.git.git.usecase.SaveUsername
import com.xquare.git.git.usecase.UpdateGit
import com.xquare.git.user.usecase.getUserId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class GitFacade(
    private val saveUsername: SaveUsername,
    private val checkUsername: CheckUsername,
    private val findAllGit: FindAllGit,
    private val updateGit: UpdateGit,
    private val getUserId: getUserId,
    private val findGitByCurrentUserId: FindGitByCurrentUserId
) {
    @Transactional(rollbackFor = [Exception::class])
    open suspend fun saveUsername(userId: String?, username: String) {
        checkUsername.execute(username)
        val uuid = getUserId.execute(userId)
        saveUsername.execute(uuid, username)
    }

    @Transactional(rollbackFor = [Exception::class])
    open suspend fun findAllGit(): FindAllUserResponse {
        return findAllGit.execute()
    }

    @Transactional(rollbackFor = [Exception::class])
    open suspend fun findGitByCurrentUserId(userId: String?): FindUserElement {
        val uuid = getUserId.execute(userId)
        return findGitByCurrentUserId.execute(uuid)
    }

    @Transactional(rollbackFor = [Exception::class])
    open suspend fun updateContributions() {
        updateGit.execute()
    }
}