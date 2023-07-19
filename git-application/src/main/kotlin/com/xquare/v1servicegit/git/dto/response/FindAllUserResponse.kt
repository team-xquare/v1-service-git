package com.xquare.v1servicegit.git.dto.response

import com.xquare.v1servicegit.git.Git
import com.xquare.v1servicegit.user.dto.response.FindUserListResponse.FindUserInfoElement
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

fun Git.toUserElement(userInfo: FindUserInfoElement) = FindAllUserResponse.FindUserElement(
    userId = this.userId,
    name = userInfo.name,
    username = this.username,
    profileFileName = userInfo.profileFileName,
    contributions = this.contributions,
    ranking = this.ranking,
)
