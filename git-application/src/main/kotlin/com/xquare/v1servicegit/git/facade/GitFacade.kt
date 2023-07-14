package com.xquare.v1servicegit.git.facade

import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse.FindUserElement
import com.xquare.v1servicegit.git.usecase.FindAllGitUseCase
import com.xquare.v1servicegit.git.usecase.FindGitByCurrentUserIdUseCase
import com.xquare.v1servicegit.git.usecase.SaveUsernameUseCase
import com.xquare.v1servicegit.git.usecase.UpdateGitUseCase
import com.xquare.v1servicegit.github.usecase.GetGithubAccessTokenUseCase
import com.xquare.v1servicegit.github.usecase.GetGithubUserInfoUseCase
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
open class GitFacade(
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val findAllGitUseCase: FindAllGitUseCase,
    private val updateGitUseCase: UpdateGitUseCase,
    private val findGitByCurrentUserIdUseCase: FindGitByCurrentUserIdUseCase,
    private val getGithubAccessTokenUseCase: GetGithubAccessTokenUseCase,
    private val getGithubUserInfoUseCase: GetGithubUserInfoUseCase,
) {
    open suspend fun saveUsername(currentUserId: UUID, code: String) {
        val token = getGithubAccessTokenUseCase.execute(code)
        val username = getGithubUserInfoUseCase.execute(token)

        saveUsernameUseCase.execute(currentUserId, username)
    }

    open suspend fun findAllGit(): FindAllUserResponse {
        return findAllGitUseCase.execute()
    }

    open suspend fun findGitByCurrentUserId(currentUserId: UUID): FindUserElement {
        return findGitByCurrentUserIdUseCase.execute(currentUserId)
    }

    @Transactional
    open suspend fun updateContributions() {
        updateGitUseCase.execute()
    }
}
