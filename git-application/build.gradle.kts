plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

dependencies {
    implementation(project(":git-domain"))

    implementationDependencies(Libraries.Transaction)
    implementationDependencies(Libraries.Component)
}