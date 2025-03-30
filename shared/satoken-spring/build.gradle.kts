plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.spring)
    id("project-conventions")
}

dependencies{

 implementation(project(":api"))
    implementation(libs.spring.boot)
    api(libs.satoken.spring)
    implementation(libs.spring.boot.webflux)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
