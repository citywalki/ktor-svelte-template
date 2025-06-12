//rootProject.name = "shared"
//
//pluginManagement {
//    includeBuild("../build-logic")
//}
//
//dependencyResolutionManagement {
//    versionCatalogs {
//        create("libs") {
//            from(files("../gradle/libs.versions.toml"))
//        }
//    }
//}
//
//include("api")
//include("security-spring")
//include("autoconfigure")
//
//dependencyResolutionManagement {
//    repositories {
//        maven("https://repo.huaweicloud.com/repository/maven/")
//        mavenCentral()
//    }
//}
dependencyResolutionManagement {
    // Use Maven Central as the default repository (where Gradle will download dependencies) in all subprojects.
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://repo.huaweicloud.com/repository/maven/")
        mavenCentral()
    }
}

plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("api")
include(":autoconfigure")

rootProject.name = "shared"
