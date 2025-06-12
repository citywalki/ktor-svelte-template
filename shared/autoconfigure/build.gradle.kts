plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.kotlin.spring)
}

dependencies {

    implementation(libs.slf4k)
    implementation(project(":api"))
    implementation(libs.spring.boot)

    compileOnly(libs.spring.boot.webmvc)

    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
