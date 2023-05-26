package com.xquare.git.git.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.git.dto.FindUserElement
import com.xquare.git.git.exceptions.GitExceptions
import com.xquare.git.git.spi.QueryGitPort
import com.xquare.git.user.spi.QueryUserPort
import java.util.*

@UseCase
class FindGitByCurrentUserId(
    private val queryGitPort: QueryGitPort,
    private val queryUserPort: QueryUserPort
) {
    suspend fun execute(userId: UUID): FindUserElement {
        val response = queryGitPort.getGitByUserId(userId) ?: throw GitExceptions.NotFound()
        return response.let {
            val name = queryUserPort.getName(it.userId).name
            FindUserElement(
                userId = it.userId,
                name = name,
                username = it.username,
                avatarUrl = it.avatarUrl,
                contributions = it.contributions
            )
        }
    }
}