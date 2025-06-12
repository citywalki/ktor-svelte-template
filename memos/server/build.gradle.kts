@file:OptIn(KspExperimental::class)

import com.google.devtools.ksp.KspExperimental

plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias { libs.plugins.google.ksp }
    alias(libs.plugins.kotlin.spring)
    alias { libs.plugins.spring.dependencymanagement }
}

dependencies {
    ksp(libs.komapper.processor)

    implementation("pro.walkin.logging:core")
    ksp("pro.walkin.logging:processor")

    api(project(":domain"))

    implementation(libs.spring.boot.webmvc)
    implementation(libs.springdoc.openapi.webmvc)

    implementation(libs.komapper.spring.boot.starter.jdbc)
    implementation(libs.komapper.dialect.postgresql.jdbc)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.markdown)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    implementation(libs.auth0.jwt)
}
ksp {
    arg("translationFilesPath", "${project.projectDir}/i18ns/")
}
