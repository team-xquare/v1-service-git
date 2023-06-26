package com.xquare.git.git.model

import com.xquare.git.annotations.Aggregate
import java.util.*

@Aggregate
data class Git(
    val userId: UUID,
    val username: String,
    val contributions: Int
) {
    fun updateGit(contributions: Int): Git {
        return copy(
            contributions = contributions
        )
    }
}
