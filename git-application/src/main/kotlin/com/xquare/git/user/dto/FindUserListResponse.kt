package com.xquare.git.user.dto

import java.util.UUID

data class FindUserListResponse(
    val userList: List<FindUserInfoElement>
)

data class FindUserInfoElement(
    val id: UUID,
    val name: String
)