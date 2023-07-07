package com.xquare.git.git.dto

import java.io.Serializable
import java.util.*

data class FindAllUserResponse(
    val users: List<FindUserElement>,
): Serializable

data class FindUserElement(
    val userId: UUID,
    val name: String,
    val username: String,
    val profileFileName: String?,
    val contributions: Int,
    val ranking: Int,
)
