package com.xquare.git.persistence.git.spi

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.singleQueryOrNull
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.selectQuery
import com.xquare.git.git.model.Git
import com.xquare.git.git.spi.GitPort
import com.xquare.git.persistence.git.mapper.GitMapper
import com.xquare.git.persistence.git.model.GitEntity
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hibernate.reactive.mutiny.Mutiny.Session
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class GitPersistenceAdapter(
    private val reactiveQueryFactory: HibernateMutinyReactiveQueryFactory,
    private val gitMapper: GitMapper,

    @Value("\${service.scheme}")
    private val scheme: String
) : GitPort {

    companion object {
        const val URL = "https://github.com"
        const val TEXT = "div.js-yearly-contributions h2"
        const val START_TAG = "<h2 class=\"f4 text-normal mb-2\">"
        const val END_TAG = "contributions in the last year</h2>"
    }

    private val scope = CoroutineScope(Dispatchers.IO + Job())

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
        val gitEntity = reactiveQueryFactory.withFactory { _ ->
            findByUserId(userId)
        }
        return gitEntity?.let { gitMapper.entityToDomain(it) }
    }

    private suspend fun findByUserId(userId: UUID): GitEntity? {
        return reactiveQueryFactory.singleQueryOrNull<GitEntity> {
            select(entity(GitEntity::class))
            from(entity(GitEntity::class))
            where(
                col(GitEntity::userId).`in`(userId)
            )
        }
    }

    override suspend fun getGitByUsername(username: String): Git? {
        val gitEntity = reactiveQueryFactory.withFactory { _ ->
            findByUsername(username)
        }
        return gitEntity?.let { gitMapper.entityToDomain(it) }
    }

    private suspend fun findByUsername(username: String): GitEntity? {
        return reactiveQueryFactory.singleQueryOrNull<GitEntity> {
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
        val url = "$URL/$username"
        val text = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get().select(TEXT).toString()
        }
        val startIndex = text.indexOf(START_TAG) + START_TAG.length
        val endIndex = text.indexOf(END_TAG)
        val contributionText = text.substring(startIndex, endIndex).replace(Regex("\\D"), "")

        return contributionText.toInt()
    }

    override suspend fun updateContributionCount(gitAllInfo: List<Git>): Map<UUID, Int> = runBlocking {
        val updateContributionCount = mutableMapOf<UUID, Int>()
        val deferredResults = gitAllInfo.map { git ->
            scope.async {
                val contribution = getContributionCount(git.username)
                git.userId to contribution
            }
        }

        deferredResults.awaitAll().forEach { (userId, contribution) ->
            updateContributionCount[userId] = contribution
        }

        updateContributionCount
    }
}
