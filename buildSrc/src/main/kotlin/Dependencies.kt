enum class ImplementationType(val originalName: String) {
    IMPLEMENTATION("implementation"),
    TEST_IMPLEMENTATION("testImplementation"),
    KAPT("kapt"),
    RUNTIME_ONLY("runtimeOnly")
}

interface Libraries {
    fun dependencies(): List<Pair<String, ImplementationType>>

    object Kotlin : Libraries {
        private const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
        private const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

        override fun dependencies() = listOf(
            KOTLIN_REFLECT to ImplementationType.IMPLEMENTATION,
            KOTLIN_STDLIB to ImplementationType.IMPLEMENTATION
        )
    }

    object JDSL : Libraries {
        private const val MUTINY_KOTLIN = "io.smallrye.reactive:mutiny-kotlin:${DependencyVersions.MUTINY_VERSION}"
        private const val MUTINY_REACTOR = "io.smallrye.reactive:mutiny-reactor:${DependencyVersions.MUTINY_VERSION}"
        private const val KOTLIN_JDSL =
            "com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:${DependencyVersions.JDSL_VERSION}"
        private const val REACTIVE_MYSQL = "io.vertx:vertx-mysql-client:${DependencyVersions.REACTIVE_MYSQL_VERSION}"
        private const val REACTIVE_HIBERNATE =
            "org.hibernate.reactive:hibernate-reactive-core:${DependencyVersions.HIBERNATE_REACTIVE_VERSION}"
        private const val SPRING_DATA_COMMON = "org.springframework.data:spring-data-commons"

        override fun dependencies() = listOf(
            MUTINY_KOTLIN to ImplementationType.IMPLEMENTATION,
            MUTINY_REACTOR to ImplementationType.IMPLEMENTATION,
            KOTLIN_JDSL to ImplementationType.IMPLEMENTATION,
            REACTIVE_MYSQL to ImplementationType.IMPLEMENTATION,
            REACTIVE_HIBERNATE to ImplementationType.IMPLEMENTATION,
            SPRING_DATA_COMMON to ImplementationType.IMPLEMENTATION
        )
    }

    object Jackson : Libraries {
        private const val MODULE_KOTLIN =
            "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"

        override fun dependencies() = listOf(
            MODULE_KOTLIN to ImplementationType.IMPLEMENTATION
        )
    }

    object Transaction : Libraries {
        private const val SPRING_TRANSACTION = "org.springframework:spring-tx:${DependencyVersions.SPRING_TRANSACTION}"

        override fun dependencies() = listOf(
            SPRING_TRANSACTION to ImplementationType.IMPLEMENTATION
        )
    }

    object Component : Libraries {
        private const val SPRING_COMPONENT = "org.springframework:spring-context:${DependencyVersions.SPRING_COMPONENT}"

        override fun dependencies() = listOf(
            SPRING_COMPONENT to ImplementationType.IMPLEMENTATION
        )
    }

    object SpringBoot : Libraries {
        private const val WEBFLUX = "org.springframework.boot:spring-boot-starter-webflux"
        private const val STARTER_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
        private const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"

        override fun dependencies() = listOf(
            WEBFLUX to ImplementationType.IMPLEMENTATION,
            STARTER_VALIDATION to ImplementationType.IMPLEMENTATION,
            SPRING_SECURITY to ImplementationType.IMPLEMENTATION,
        )
    }

    object CloudConfig : Libraries {
        private const val CLOUD_CONFIG = "org.springframework.cloud:spring-cloud-config-client"

        override fun dependencies() = listOf(
            CLOUD_CONFIG to ImplementationType.IMPLEMENTATION
        )
    }

    object Jsoup : Libraries {
        private const val JSOUP = "org.jsoup:jsoup:${DependencyVersions.JSOUP_VERSION}"

        override fun dependencies() = listOf(
            JSOUP to ImplementationType.IMPLEMENTATION,
        )
    }

    object Coroutine : Libraries {
        private const val REACTOR_COROUTINE_EXTENSION = "io.projectreactor.kotlin:reactor-kotlin-extensions"
        private const val COROUTINE_REACTOR =
            "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${DependencyVersions.COROUTINE_VERSION}"
        private const val COROUTINE_JDK =
            "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${DependencyVersions.COROUTINE_VERSION}"
        private const val KOTLINX_COROUTINE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.COROUTINE_VERSION}"

        override fun dependencies() = listOf(
            REACTOR_COROUTINE_EXTENSION to ImplementationType.IMPLEMENTATION,
            COROUTINE_REACTOR to ImplementationType.IMPLEMENTATION,
            COROUTINE_JDK to ImplementationType.RUNTIME_ONLY,
            KOTLINX_COROUTINE to ImplementationType.IMPLEMENTATION
        )
    }
}

object Dependencies {
    const val KTLINT = "com.pinterest:ktlint: ${DependencyVersions.KTLINT_VERSION}"
    const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}"
}