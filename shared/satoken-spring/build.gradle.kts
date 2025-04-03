plugins {
    kotlin("jvm")
    id("project-conventions")
}

dependencies {

    implementation(project(":api"))
    implementation(libs.spring.boot)
    api(libs.satoken.spring)
    implementation(libs.spring.boot.webflux)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)
}
