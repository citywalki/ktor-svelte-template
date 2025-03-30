plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.spring)
    id("project-conventions")
}

dependencies{

 implementation(project(":api"))
    implementation(libs.spring.boot)
    compileOnly(project(":security-spring"))
    compileOnly(project(":satoken-spring"))
    compileOnly(project(":usecase-spring"))

    compileOnly(libs.satoken.spring)
    compileOnly(libs.spring.boot.webflux)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
