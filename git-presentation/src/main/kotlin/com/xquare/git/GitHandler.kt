package com.xquare.git

import com.xquare.git.git.dto.FindAllUserResponse
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.facade.GitFacade
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

@Component
class GitHandler(
    private val gitFacade: GitFacade,
    ) {
    suspend fun saveUsername(serverRequest: ServerRequest): ServerResponse {
        val currentUserId = serverRequest.headers().firstHeader("Request-User-Id")
        val username = serverRequest.queryParam("username").orElse("")
        gitFacade.saveUsername(currentUserId, username)

        return ServerResponse.created(URI("/gits")).buildAndAwait()
    }

    suspend fun getAllGit(serverRequest: ServerRequest): ServerResponse {
        val gitResponse: FindAllUserResponse = gitFacade.findAllGit()
        return ServerResponse.ok().bodyValueAndAwait(gitResponse)
    }

    suspend fun getCurrentGit(serverRequest: ServerRequest): ServerResponse {
        val userId = serverRequest.headers().firstHeader("Request-User-Id")
        val gitResponse: FindUserElement = gitFacade.findGitByCurrentUserId(userId)
        return ServerResponse.ok().bodyValueAndAwait(gitResponse)
    }

    suspend fun updateContributions(serverRequest: ServerRequest): ServerResponse {
        gitFacade.updateContributions()
        return ServerResponse.noContent().buildAndAwait()
    }
}