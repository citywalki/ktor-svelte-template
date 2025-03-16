rootProject.name = "memos"
pluginManagement {
    includeBuild("../gradle-plugins")
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

includeBuild("../shared")
include(":server")
include(":domain")

