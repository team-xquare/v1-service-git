package com.xquare.git.persistence.git.spi

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.selectQuery
import com.linecorp.kotlinjdsl.singleQueryOrNull
import com.xquare.git.git.model.Git
import com.xquare.git.git.spi.GitPort
import com.xquare.git.global.exceptions.GlobalExceptions
import com.xquare.git.persistence.git.mapper.GitMapper
import com.xquare.git.persistence.git.model.GitEntity
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.hibernate.reactive.mutiny.Mutiny.Session
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*

@Component
class GitPersistenceAdapter(
    private val reactiveQueryFactory: HibernateMutinyReactiveQueryFactory,
    private val gitMapper: GitMapper,
    private val webClient: WebClient,
    
    @Value("\${service.scheme}")
    private val scheme: String
) : GitPort {

    override suspend fun saveUser(git: Git) {
        val gitEntity = gitMapper.domainToEntity(git)
        reactiveQueryFactory.transactionWithFactory { session, _ ->
            session.persistGitEntityConcurrently(gitEntity)
        }
    }

    private suspend fun Session.persistGitEntityConcurrently(gitEntity: GitEntity) = coroutineScope {
        launch {
            this@persistGitEntityConcurrently.persist(gitEntity).awaitSuspending()
        }
    }

    override suspend fun getAllGit(): List<Git> {
        return reactiveQueryFactory.withFactory { _, reactiveQueryFactory ->
            reactiveQueryFactory.findAll()
        }.map { gitMapper.entityToDomain(it) }
    }

    private suspend fun ReactiveQueryFactory.findAll(): List<GitEntity> {
        return this.selectQuery<GitEntity> {
            select(entity(GitEntity::class))
            from(entity(GitEntity::class))
        }.resultList()
    }

    override suspend fun getGitByUserId(userId: UUID): Git? {
        val gitEntity = reactiveQueryFactory.withFactory { _, reactiveQueryFactory ->
            reactiveQueryFactory.findByUserId(userId)
        }
        return gitEntity?.let { gitMapper.entityToDomain(it) }
    }

    private suspend fun ReactiveQueryFactory.findByUserId(userId: UUID): GitEntity? {
        return this.singleQueryOrNull<GitEntity> {
            select(entity(GitEntity::class))
            from(entity(GitEntity::class))
            where(
                col(GitEntity::userId).`in`(userId)
            )
        }
    }

    override suspend fun getGitByUsername(username: String): Git? {
        val gitEntity = reactiveQueryFactory.withFactory { _, reactiveQueryFactory ->
            reactiveQueryFactory.findByUsername(username)
        }
        return gitEntity?.let { gitMapper.entityToDomain(it) }
    }

    private suspend fun ReactiveQueryFactory.findByUsername(username: String): GitEntity? {
        return this.singleQueryOrNull<GitEntity> {
            select(entity(GitEntity::class))
            from(entity(GitEntity::class))
            where(
                col(GitEntity::username).`in`(username)
            )
        }
    }

    override suspend fun updateGit(git: Git) {
        val gitEntity = gitMapper.domainToEntity(git)
        reactiveQueryFactory.transactionWithFactory { session, _ ->
            session.mergeUserEntity(gitEntity)
        }
    }

    private suspend fun Session.mergeUserEntity(gitEntity: GitEntity) = coroutineScope {
        launch {
            this@mergeUserEntity.merge(gitEntity).awaitSuspending()
        }
    }

    override suspend fun getContributionCount(username: String): Int {
        val url = "https://github.com/$username"
        val docs = Jsoup.connect(url).get()
        val text = docs.select("div.js-yearly-contributions h2").toString()
        val startTag = "<h2 class=\"f4 text-normal mb-2\">"
        val endTag = " contributions in the last year</h2>"
        val startIndex = text.indexOf(startTag) + startTag.length
        val endIndex = text.indexOf(endTag)
        return text.substring(startIndex, endIndex).replace(",", "").toInt()
    }

    override suspend fun getAvatarUrl(username: String): String {
        return webClient.get().uri {
            it.scheme(scheme)
                .host("api.github.com")
                .path("/users/{username}")
                .build(username)
        }.retrieve()
            .onStatus(HttpStatus::isError) {
                throw GlobalExceptions.BadRequest()
            }.awaitBody()
    }
}