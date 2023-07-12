package com.xquare.v1servicegit.git

fun GitEntity.entityToDomain() = Git(
    userId = this.userId,
    username = this.username,
    contributions = this.contributions,
    ranking = this.ranking,
)

fun Git.domainToEntity() = GitEntity(
    userId = this.userId,
    username = this.username,
    contributions = this.contributions,
    ranking = this.ranking,
)
