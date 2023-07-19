package com.xquare.git.git

import com.xquare.v1servicegit.git.Git
import java.util.UUID

fun createGit(
    id: UUID = UUID.randomUUID(),
    username: String = "testUsername",
    contributions: Int = 1,
    ranking: Int = 1,
) = Git(
    userId = id,
    username =  username,
    contributions = contributions,
    ranking = ranking
)
