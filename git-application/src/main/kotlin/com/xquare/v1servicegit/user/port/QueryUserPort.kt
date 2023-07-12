package com.xquare.v1servicegit.user.port

import com.xquare.v1servicegit.git.dto.request.FindUserInfoRequest
import com.xquare.v1servicegit.user.dto.FindUserInfoElement
import com.xquare.v1servicegit.user.dto.FindUserListResponse
import java.util.*

interface QueryUserPort {
    suspend fun getNameAndProfileFileName(userId: UUID): FindUserInfoElement
    suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse
}