package com.xquare.git.github.spi

import com.xquare.git.github.dto.GetGithubUserInfoResponse
import com.xquare.git.github.dto.TokenResponse

interface QueryGithubPort {
    suspend fun getAccessToken(code: String): TokenResponse
    suspend fun getGithubUserInfo(token: String): GetGithubUserInfoResponse
}
