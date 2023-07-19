package com.xquare.v1servicegit.git.router

import com.xquare.v1servicegit.git.usecase.GitUseCase
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
    private val gitUseCase: GitUseCase,
) {
    suspend fun saveGithubUserInfo(serverRequest: ServerRequest): ServerResponse {
        val currentUserId = requestHeaderAspect.getCurrentUserId(serverRequest)
        val code = serverRequest.queryParam("code").orElse("")
        gitUseCase.saveGithubUserInfo(currentUserId, code)

        return ServerResponse.created(URI("/gits")).buildAndAwait()
    }

    suspend fun getAllGithubInfo(serverRequest: ServerRequest): ServerResponse {
        val allGithubInfo = gitUseCase.getAllGithubInfo()
        return ServerResponse.ok().bodyValueAndAwait(allGithubInfo)
    }

    suspend fun getMyGithubInfo(serverRequest: ServerRequest): ServerResponse {
        val currentUserId = requestHeaderAspect.getCurrentUserId(serverRequest)
        val myGithubInfo = gitUseCase.getMyGithubInfo(currentUserId)
        return ServerResponse.ok().bodyValueAndAwait(myGithubInfo)
    }

    suspend fun updateContributions(serverRequest: ServerRequest): ServerResponse {
        gitUseCase.updateGitInfo()
        return ServerResponse.noContent().buildAndAwait()
    }
}
