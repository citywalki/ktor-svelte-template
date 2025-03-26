plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.google.ksp)
    id("project-conventions")
    alias { libs.plugins.spring.boot }
    alias { libs.plugins.spring.dependencymanagement }
}


dependencies {
    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.actuator)
    developmentOnly(libs.spring.boot.docker.compose)
    developmentOnly(libs.springdoc.openapi.webflux)

    api(project(":domain"))

    implementation(libs.spring.boot)
    implementation(libs.spring.boot.log4j2)
    implementation(libs.spring.boot.webflux)

    implementation("com.github.walkin.shared:usecase-spring")
    implementation("com.github.walkin.shared:security-spring")

    implementation(libs.liquibase.core)
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.data:spring-data-commons")

    implementation(libs.komapper.dialect.postgresql)
    ksp(libs.komapper.processor)
    api(libs.komapper.r2dbc.starter)

    api("com.auth0:java-jwt:4.5.0")

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.markdown)
    implementation(libs.shared.api)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reactor.extensions)

    testImplementation("org.springframework.graphql:spring-graphql-test")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

springBoot {
    buildInfo()
}

ksp {
    arg("komapper.enableEntityStoreContext", "true")

}

