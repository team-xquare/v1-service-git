package com.xquare.v1servicegit.github.port

import com.xquare.v1servicegit.github.dto.response.GetGithubUserInfoResponse
import com.xquare.v1servicegit.github.dto.response.TokenResponse

interface QueryGithubPort {
    suspend fun getAccessToken(code: String): TokenResponse
    suspend fun getGithubUserInfo(token: String): GetGithubUserInfoResponse
}
