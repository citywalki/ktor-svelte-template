plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.spring)
    id("project-conventions")
}

dependencies {

    implementation(libs.slf4k)
    implementation(project(":api"))
    implementation(libs.spring.boot)
    compileOnly(project(":webflux-security-spring"))
    compileOnly(project(":usecase-spring"))

    compileOnly(libs.satoken.spring)
    compileOnly(libs.satoken.reactor.spring)

    compileOnly(libs.spring.boot.webflux)
    compileOnly(libs.spring.boot.webmvc)

    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
