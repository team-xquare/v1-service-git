package com.xquare.v1servicegit.github.port

import com.xquare.v1servicegit.github.dto.GetGithubUserInfoResponse
import com.xquare.v1servicegit.github.dto.TokenResponse

interface QueryGithubPort {
    suspend fun getAccessToken(code: String): TokenResponse
    suspend fun getGithubUserInfo(token: String): GetGithubUserInfoResponse
}
