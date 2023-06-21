package com.xquare.git.user.spi

import com.xquare.git.git.dto.FindUserInfoRequest
import com.xquare.git.user.dto.FindUserInfoElement
import com.xquare.git.user.dto.FindUserListResponse
import java.util.UUID

interface QueryUserPort {
    suspend fun getName(userId: UUID): FindUserInfoElement
    suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse
}