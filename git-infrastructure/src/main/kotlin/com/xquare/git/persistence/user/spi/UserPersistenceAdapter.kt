package com.xquare.git.persistence.user.spi

import com.xquare.git.global.exceptions.GlobalExceptions
import com.xquare.git.user.dto.FindNameResponse
import com.xquare.git.user.spi.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import java.util.*

@Component
class UserPersistenceAdapter(
    private val webClient: WebClient,

    @Value("\${service.user.host}")
    private val userHost: String,

    @Value("\${service.scheme}")
    private val scheme: String
): UserPort {

    override suspend fun getName(userId: UUID): FindNameResponse {
        return webClient.get().uri {
            it.scheme(scheme)
                .host(userHost)
                .path("/users/id/{user-id}")
                .build(userId)
        }.retrieve()
            .onStatus(HttpStatus::isError) {
                throw GlobalExceptions.BadRequest()
            }
            .awaitBody()
    }
}