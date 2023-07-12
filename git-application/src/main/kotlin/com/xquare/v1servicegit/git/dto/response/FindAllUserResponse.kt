package com.xquare.v1servicegit.git.dto.response

import java.io.Serializable
import java.util.UUID

data class FindAllUserResponse(
    val users: List<FindUserElement>,
) : Serializable {
    data class FindUserElement(
        val userId: UUID,
        val name: String,
        val username: String,
        val profileFileName: String?,
        val contributions: Int,
        val ranking: Int,
    )
}
