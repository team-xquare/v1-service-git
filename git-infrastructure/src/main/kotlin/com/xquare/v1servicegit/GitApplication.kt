package com.xquare.v1servicegit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

internal const val BASE_PACKAGE = "com.xquare.v1servicegit"

@SpringBootApplication
class GitApplication

fun main(args: Array<String>) {
    runApplication<GitApplication>(*args)
}
