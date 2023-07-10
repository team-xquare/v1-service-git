package com.xquare.git.persistence.github.spi

import com.xquare.git.github.dto.GetGithubUserInfoResponse
import com.xquare.git.github.dto.TokenResponse
import com.xquare.git.github.spi.GithubPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class GithubPersistenceAdapter(
    private val webClient: WebClient,

    @Value("\${service.scheme}")
    private val scheme: String,

    @Value("\${auth.github.client-id}")
    private val clientId: String,

    @Value("\${auth.github.client-secret}")
    private val clientSecret: String,
) : GithubPort {
    override suspend fun getAccessToken(code: String): TokenResponse {
        return webClient.get()
            .uri {
                it.scheme(scheme)
                    .host("github.com")
                    .path("/login/oauth/access_token")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("code", code)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }

    override suspend fun getGithubUserInfo(token: String): GetGithubUserInfoResponse {
        return webClient.get()
            .uri {
                it.scheme(scheme)
                    .host("api.github.com")
                    .path("/user")
                    .build()
            }
            .header("Authorization", "token $token")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }
}
