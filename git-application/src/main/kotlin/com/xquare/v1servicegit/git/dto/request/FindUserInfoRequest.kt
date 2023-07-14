package com.xquare.v1servicegit.git.dto.request

import java.util.UUID

data class FindUserInfoRequest(
    val userIds: List<UUID>,
)
