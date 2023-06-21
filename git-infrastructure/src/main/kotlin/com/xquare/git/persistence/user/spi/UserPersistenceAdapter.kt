package com.xquare.git.persistence.user.spi

import com.xquare.git.git.dto.FindUserInfoRequest
import com.xquare.git.global.exceptions.GlobalExceptions
import com.xquare.git.user.dto.FindUserInfoElement
import com.xquare.git.user.dto.FindUserListResponse
import com.xquare.git.user.spi.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class UserPersistenceAdapter(
    private val webClient: WebClient,

    @Value("\${service.user.host}")
    private val userHost: String,

    @Value("\${service.scheme}")
    private val scheme: String
): UserPort {

    override suspend fun getName(userId: UUID): FindUserInfoElement {
        val uri = UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(userHost)
            .path("/users/id/{user-id}")
            .build(userId)

        return webClient.get()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError) {
                println(it.statusCode())
                throw GlobalExceptions.BadRequest()
            }
            .onStatus(HttpStatus::is5xxServerError) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }

    override suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse {
        val uri = UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(userHost)
            .path("/users/id")
            .build()
            .toUri()

        return webClient.post()
            .uri(uri)
            .bodyValue(findUserInfoRequest)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) {
                throw GlobalExceptions.BadRequest()
            }
            .onStatus(HttpStatus::is5xxServerError) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }
}