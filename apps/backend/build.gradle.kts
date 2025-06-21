plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.ksp)
}

val isDevelopment: Boolean = project.ext.has("development")
application {
    mainClass = "io.ktor.server.cio.EngineMain"
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    ksp(libs.komapper.processor)
    ksp("pro.walkin.logging:processor")

    implementation(libs.komapper.starter.r2dbc)
    implementation(libs.komapper.dialect.postgresql.r2dbc)
    implementation(libs.cryptography.jdk)

    implementation("pro.walkin.logging:core")
    implementation("pro.walkin.shared:shared-utils")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation(libs.ktor.server.di)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)

    if (isDevelopment) {
        implementation(libs.testcontainers.postgres)
        implementation(libs.testcontainers.r2dbc)
    }

    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.server.test.host)
//    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.komapper.dialect.h2.r2dbc)
    testImplementation(libs.ktor.client.content.negotiation)
//    testImplementation(libs.testcontainers.postgres)
    testImplementation("org.testcontainers:junit-jupiter:1.21.1")
    testImplementation("io.mockk:mockk:1.14.2")
}

ksp {
    arg("translationFilesPath", "${project.projectDir}/i18n/")
}
