package com.xquare.git.persistence.git.mapper

import com.xquare.git.git.model.Git
import com.xquare.git.persistence.git.model.GitEntity
import org.springframework.stereotype.Component

@Component
class GitMapper {
    fun entityToDomain(entity: GitEntity): Git {
        return entity.run {
            Git(
                userId = userId,
                username = username,
                contributions = contributions,
                ranking = ranking
            )
        }
    }

    fun domainToEntity(domain: Git): GitEntity {
        return domain.run {
            GitEntity(
                userId = userId,
                username = username,
                contributions = contributions,
                ranking = ranking
            )
        }
    }
}