plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    id("project-conventions")
    alias { libs.plugins.spring.boot }
    alias { libs.plugins.spring.dependencymanagement }
}

dependencies {
    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.actuator)
    developmentOnly(libs.spring.boot.docker.compose)
    developmentOnly(libs.springdoc.openapi.webmvc)

    api(project(":domain"))

    implementation(libs.spring.boot)
    implementation(libs.spring.boot.log4j2)
    implementation(libs.spring.boot.webmvc)
    implementation(libs.satoken.spring)

    implementation("com.github.walkin.shared:usecase-spring")
    implementation("com.github.walkin.shared:autoconfigure")

    implementation(libs.liquibase.core)

    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.postgresql:postgresql:42.7.5")
    api(libs.exposed.starter)
    api(libs.exposed.kotlin.datetime)

    api("com.auth0:java-jwt:4.5.0")

    implementation("com.graphql-java:graphql-java-extended-scalars:22.0")
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
