dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/krpc/grpc")
    }
    includeBuild("../gradle-plugins")
}

rootProject.name = "memo-kt"

include("domain")
include("service")
