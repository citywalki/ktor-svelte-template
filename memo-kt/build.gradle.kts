val koin_version: String by project
val kotlin_version: String by project

plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.serialization)
    id("project-conventions")
    id("io.ktor.plugin") version "3.1.2"
}

group = "pro.walkin.memo"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":domain"))
    api(project(":service"))

    implementation(libs.testcontainers)
    implementation(libs.testcontainers.postgres)

    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-core")
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
//    implementation("io.ktor:ktor-server-host-common:3.1.2")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-call-logging")
//    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-call-id")
//    implementation("dev.hayden:khealth:3.0.2")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    implementation(libs.cryptography.core)
    implementation(libs.cryptography.jdk)
    implementation(libs.jwtKt)
//    implementation("com.appstractive:ktor-server-auth-jwt:1.1.0")
//    implementation("com.appstractive:jwt-hmac-kt:1.1.0")

    implementation(libs.komapper.r2dbc)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
