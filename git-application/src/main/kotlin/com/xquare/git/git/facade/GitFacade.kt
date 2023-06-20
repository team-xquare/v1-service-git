package com.xquare.git.git.facade

import com.xquare.git.git.dto.FindAllUserResponse
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.usecase.CheckUsernameUseCase
import com.xquare.git.git.usecase.FindAllGitUseCase
import com.xquare.git.git.usecase.FindGitByCurrentUserIdUseCase
import com.xquare.git.git.usecase.SaveUsernameUseCase
import com.xquare.git.git.usecase.UpdateGitUseCase
import com.xquare.git.user.usecase.GetUserIdUseCase
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class GitFacade(
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val findAllGit: FindAllGitUseCase,
    private val updateGit: UpdateGitUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val findGitByCurrentUserId: FindGitByCurrentUserIdUseCase
) {
    open suspend fun saveUsername(currentUserId: String?, username: String) {
        checkUsernameUseCase.execute(username)
        val userId = getUserIdUseCase.execute(currentUserId)
        saveUsernameUseCase.execute(userId, username)
    }

    open suspend fun findAllGit(): FindAllUserResponse {
        return findAllGit.execute()
    }

    open suspend fun findGitByCurrentUserId(userId: String?): FindUserElement {
        val uuid = getUserIdUseCase.execute(userId)
        return findGitByCurrentUserId.execute(uuid)
    }

    @Transactional
    open suspend fun updateContributions() {
        updateGit.execute()
    }
}