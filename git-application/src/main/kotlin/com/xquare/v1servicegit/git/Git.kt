package com.xquare.v1servicegit.git

import com.xquare.v1servicegit.common.annotations.Aggregate
import java.util.UUID

@Aggregate
data class Git(
    val userId: UUID,
    val username: String,
    val contributions: Int,
    val ranking: Int,
) {
    fun updateGit(contributions: Int, ranking: Int): Git {
        return copy(
            contributions = contributions,
            ranking = ranking,
        )
    }
}
