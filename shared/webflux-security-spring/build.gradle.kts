plugins {
    id("module")
    alias(libs.plugins.kotlin.spring)
}

dependencies {

    implementation(project(":api"))
    implementation(libs.slf4k)
    implementation(libs.spring.boot)
    api(libs.spring.boot.security)
    implementation(libs.spring.boot.webflux)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)
}
