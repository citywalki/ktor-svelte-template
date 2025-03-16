plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.google.ksp)
    id("project-conventions")
}


dependencies {
    api(project(":domain"))

    implementation(libs.komapper.dialect.postgresql)
    ksp(libs.komapper.processor)
    api(libs.komapper.r2dbc.starter)


    implementation(libs.spring.boot.webflux)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.markdown)
    implementation(libs.shared.api)
    implementation(libs.kotlinx.serialization.protobuf)

//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

ksp {
    arg("komapper.enableEntityStoreContext", "true")

}

