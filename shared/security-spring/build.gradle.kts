plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.spring)
    id("project-conventions")
}

dependencies{

 implementation(project(":api"))
    implementation(libs.spring.boot)
    api(libs.spring.boot.webflux)
    implementation(libs.satoken.starter)
    implementation(libs.satoken.jwt)

    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
