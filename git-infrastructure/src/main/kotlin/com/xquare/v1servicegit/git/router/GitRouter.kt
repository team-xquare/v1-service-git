package com.xquare.v1servicegit.git.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class GitRouter {
    @Bean
    fun gitBaseRouter(gitHandler: GitHandler) = coRouter {
        "/gits".nest {
            contentType(MediaType.APPLICATION_JSON)
            POST("", gitHandler::saveGithubUserInfo)
            GET("/all", gitHandler::getAllGithubInfo)
            GET("", gitHandler::getMyGithubInfo)
            PATCH("", gitHandler::updateContributions)
        }
    }
}
