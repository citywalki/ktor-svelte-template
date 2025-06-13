plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencymanagement)
}

dependencies {

    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.actuator)
    developmentOnly(libs.spring.boot.docker.compose)

    implementation(libs.spring.boot)
    implementation(libs.spring.boot.log4j2)

    implementation(libs.shared.autoconfigure)
    implementation(libs.shared.api)

    implementation(project(":server"))
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

tasks.bootRun {
    systemProperty("spring.docker.compose.file", rootDir.path + "/compose.yaml")
}

springBoot {
    buildInfo()
}
