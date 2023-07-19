package com.xquare.v1servicegit.user.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class FindUserListResponse(
    val users: List<FindUserInfoElement>,
) {
    data class FindUserInfoElement(
        val id: UUID,
        val name: String,

        @JsonProperty("profile_file_name")
        val profileFileName: String?,
    )
}
