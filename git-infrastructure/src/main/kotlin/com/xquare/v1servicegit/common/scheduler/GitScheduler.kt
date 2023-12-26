package com.xquare.v1servicegit.common.scheduler

import com.xquare.v1servicegit.common.util.logger
import com.xquare.v1servicegit.git.usecase.GitUseCase
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class GitScheduler(
    private val gitUseCase: GitUseCase,
) {
    @Scheduled(cron = "* 30 * * * *")
    fun gitScheduler() {
        runBlocking { gitUseCase.updateGitInfo() }
        logger().info("update success")
    }
}
