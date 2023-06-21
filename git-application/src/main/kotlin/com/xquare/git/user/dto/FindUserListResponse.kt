package com.xquare.git.user.dto

import java.util.*

data class FindUserListResponse(
    val users: List<FindUserInfoElement>,
)

data class FindUserInfoElement(
    val id: UUID,
    val name: String,
)
