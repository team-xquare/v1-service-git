package com.xquare.git.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class FindUserListResponse(
    val users: List<FindUserInfoElement>,
)

data class FindUserInfoElement(
    val id: UUID,
    val name: String,

    @JsonProperty("profile_file_name")
    val profileFileName: String?,
)
