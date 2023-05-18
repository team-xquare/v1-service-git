plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

dependencies {
    implementation(project(":git-application"))

    implementationDependencies(Libraries.SpringBoot)
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}