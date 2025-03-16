plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.spring)
    alias { libs.plugins.spring.boot }
    alias { libs.plugins.spring.dependencymanagement }
    id("project-conventions")
}

allprojects {
    group = "com.github.walkin.memos"
    version = "0.0.1-SNAPSHOT"
}

dependencies {
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.log4j2)

    implementation("com.github.walkin.shared:usecase-spring")
    implementation("com.github.walkin.shared:security-spring")

    api(project(":server"))
    implementation(libs.liquibase.core)

    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.actuator)
    developmentOnly(libs.spring.boot.docker.compose)
    developmentOnly(libs.springdoc.openapi.webflux)
    developmentOnly(libs.komapper.dialect.h2)
}

springBoot {
    buildInfo()
}

tasks.spotlessApply{
    dependsOn(gradle.includedBuilds.filter { it.name != "gradle-plugins" }.map { it.task(":spotlessApply") })
}
