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
    private val findAllGitUseCase: FindAllGitUseCase,
    private val updateGitUseCase: UpdateGitUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val findGitByCurrentUserId: FindGitByCurrentUserIdUseCase,
) {
    open suspend fun saveUsername(currentUserId: String?, username: String) {
        checkUsernameUseCase.execute(username)
        val userId = getUserIdUseCase.execute(currentUserId)
        saveUsernameUseCase.execute(userId, username)
    }

    open suspend fun findAllGit(): FindAllUserResponse {
        return findAllGitUseCase.execute()
    }

    open suspend fun findGitByCurrentUserId(currentUserId: String?): FindUserElement {
        val userId = getUserIdUseCase.execute(currentUserId)
        return findGitByCurrentUserId.execute(userId)
    }

    @Transactional
    open suspend fun updateContributions() {
        updateGitUseCase.execute()
    }
}