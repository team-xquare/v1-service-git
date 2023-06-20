package com.xquare.git.user.spi

import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.user.dto.FindUserListResponse
import java.util.*

interface QueryUserPort {
    suspend fun getName(userId: UUID): FindUserElement
    suspend fun getAllUserInfo(userId: List<UUID>): FindUserListResponse
}