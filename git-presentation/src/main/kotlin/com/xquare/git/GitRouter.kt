package com.xquare.git

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
            POST("", gitHandler::saveUsername)
            GET("/all", gitHandler::getAllGit)
            GET("", gitHandler::getCurrentGit)
            PATCH("", gitHandler::updateContributions)
        }
    }
}
