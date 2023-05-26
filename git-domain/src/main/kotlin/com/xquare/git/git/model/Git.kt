package com.xquare.git.git.model

import com.xquare.git.annotations.Aggregate
import java.util.*

@Aggregate
data class Git(
    val userId: UUID,
    val username: String,
    val avatarUrl: String,
    val contributions: Int
) {
    fun updateGit(avatarUrl: String, contributions: Int): Git {
        return copy(
            avatarUrl = avatarUrl,
            contributions = contributions
        )
    }
}
