package com.xquare.git.persistence.git.model

import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_git")
class GitEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,

    @field: NotNull
    val username: String,

    contributions: Int,

    avatarUrl: String

) {

    @field: NotNull
    var contributions: Int = contributions
        protected set

    @field: NotNull
    var avatarUrl: String = avatarUrl
        protected set
}