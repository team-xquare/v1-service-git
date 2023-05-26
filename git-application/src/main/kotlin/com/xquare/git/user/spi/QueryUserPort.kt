package com.xquare.git.user.spi

import com.xquare.git.user.dto.FindNameResponse
import java.util.*

interface QueryUserPort {
    suspend fun getName(userId: UUID): FindNameResponse
}