package com.xquare.v1servicegit.router

import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse
import com.xquare.v1servicegit.git.dto.response.FindAllUserResponse.FindUserElement
import com.xquare.v1servicegit.git.facade.GitFacade
import com.xquare.v1servicegit.user.aop.RequestHeaderAspect
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

@Component
class GitHandler(
    private val requestHeaderAspect: RequestHeaderAspect,
    private val gitFacade: GitFacade,
) {
    suspend fun saveUsername(serverRequest: ServerRequest): ServerResponse {
        val currentUserId = requestHeaderAspect.getCurrentUserId(serverRequest)
        val code = serverRequest.queryParam("code").orElse("")
        gitFacade.saveUsername(currentUserId, code)

        return ServerResponse.created(URI("/gits")).buildAndAwait()
    }

    suspend fun getAllGit(serverRequest: ServerRequest): ServerResponse {
        val gitResponse: FindAllUserResponse = gitFacade.findAllGit()
        return ServerResponse.ok().bodyValueAndAwait(gitResponse)
    }

    suspend fun getCurrentGit(serverRequest: ServerRequest): ServerResponse {
        val currentUserId = requestHeaderAspect.getCurrentUserId(serverRequest)
        val gitResponse: FindUserElement = gitFacade.findGitByCurrentUserId(currentUserId)
        return ServerResponse.ok().bodyValueAndAwait(gitResponse)
    }

    suspend fun updateContributions(serverRequest: ServerRequest): ServerResponse {
        gitFacade.updateContributions()
        return ServerResponse.noContent().buildAndAwait()
    }
}
