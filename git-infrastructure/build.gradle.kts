plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
}

dependencies {
    implementation(project(":git-presentation"))
    implementation(project(":git-application"))
    implementation(project(":git-domain"))

    implementationDependencies(Libraries.SpringBoot)
    implementationDependencies(Libraries.Kotlin)
    implementationDependencies(Libraries.JDSL)
    implementationDependencies(Libraries.Jsoup)
    implementationDependencies(Libraries.Coroutine)
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}