package com.xquare.v1servicegit.user.aop

import com.xquare.v1servicegit.user.exceptions.UserExceptions
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID

@Component
class RequestHeaderAspect {

    fun getCurrentUserId(serverRequest: ServerRequest): UUID {
        val currentUserId = serverRequest.headers().firstHeader("Request-User-Id")
            ?: throw UserExceptions.UnAuthorized()

        return UUID.fromString(currentUserId)
    }
}
