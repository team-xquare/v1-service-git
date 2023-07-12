package com.xquare.v1servicegit.git

import org.jetbrains.annotations.NotNull
import java.util.UUID
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

    @field:NotNull
    val username: String,

    contributions: Int,

    ranking: Int,
) {

    @field:NotNull
    var contributions: Int = contributions
        protected set

    @field:NotNull
    var ranking: Int = ranking
        protected set
}
