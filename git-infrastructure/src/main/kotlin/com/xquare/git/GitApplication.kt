package com.xquare.git

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

internal const val BASE_PACKAGE = "com.xquare.git"

@SpringBootApplication
class GitApplication

fun main(args: Array<String>) {
    runApplication<GitApplication>(*args)
}
