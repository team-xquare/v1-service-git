package com.xquare.v1servicegit.user.port

import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse.FindUserInfoElement
import java.util.UUID

interface QueryUserPort {
    suspend fun getNameAndProfileFileName(userId: UUID): FindUserInfoElement
    suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse
}
