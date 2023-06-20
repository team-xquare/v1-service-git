package com.xquare.git.persistence.user.spi

import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.global.exceptions.GlobalExceptions
import com.xquare.git.user.dto.FindUserListResponse
import com.xquare.git.user.spi.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*

@Component
class UserPersistenceAdapter(
    private val webClient: WebClient,

    @Value("\${service.user.host}")
    private val userHost: String,

    @Value("\${service.scheme}")
    private val scheme: String
): UserPort {

    override suspend fun getName(userId: UUID): FindUserElement {
        return webClient.get().uri {
            it.scheme(scheme)
                .host(userHost)
                .path("/users/id/{user-id}")
                .build(userId)
        }.retrieve()
            .onStatus(HttpStatus::is4xxClientError) {
                throw GlobalExceptions.BadRequest()
            }
            .onStatus(HttpStatus::is5xxServerError) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }

    override suspend fun getAllUserInfo(userId: List<UUID>): FindUserListResponse {
        return webClient.post().uri {
            it.scheme(scheme)
                .host(userHost)
                .path("/users/id")
                .build(userId)
        }.retrieve()
            .onStatus(HttpStatus::is4xxClientError) {
                throw GlobalExceptions.BadRequest()
            }
            .onStatus(HttpStatus::is5xxServerError) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }
}