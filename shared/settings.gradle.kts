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
include("autoconfigure")
include("usecase-spring")
include("webflux-security-spring")
include("satoken-spring")


