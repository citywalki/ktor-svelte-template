rootProject.name = "shared"
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

include("api")
include("usecase-spring")
include("security-spring")


