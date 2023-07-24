plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

dependencies {
    implementationDependencies(Libraries.Transaction)
    implementationDependencies(Libraries.Component)
    implementationDependencies(Libraries.Test)
}