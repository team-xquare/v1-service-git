package com.xquare.v1servicegit.user

import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.user.dto.FindUserInfoElement
import com.xquare.v1servicegit.user.dto.FindUserListResponse
import com.xquare.v1servicegit.user.port.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

@Component
class UserPersistenceAdapter(
    private val webClient: WebClient,

    @Value("\${service.user.host}")
    private val userHost: String,

    @Value("\${service.scheme}")
    private val scheme: String
) : UserPort {

    override suspend fun getNameAndProfileFileName(userId: UUID): FindUserInfoElement =
        webClient.get()
            .uri { uri ->
                uri.scheme(scheme)
                    .host(userHost)
                    .path("/users/id/{user-id}")
                    .build(userId)
            }
            .retrieve()
            .awaitBody()

    override suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse =
        webClient.post()
            .uri { uri ->
                uri.scheme(scheme)
                    .host(userHost)
                    .path("/users/id")
                    .build()
            }
            .bodyValue(findUserInfoRequest)
            .retrieve()
            .awaitBody()
}
