package com.xquare.git.user.spi

import com.xquare.git.git.dto.FindUserInfoRequest
import com.xquare.git.user.dto.FindUserInfoElement
import com.xquare.git.user.dto.FindUserListResponse
import java.util.*

interface QueryUserPort {
    suspend fun getNameAndProfileFileName(userId: UUID): FindUserInfoElement
    suspend fun getAllUserInfo(findUserInfoRequest: FindUserInfoRequest): FindUserListResponse
}