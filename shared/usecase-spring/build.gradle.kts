plugins {
    kotlin("jvm")
    id("project-conventions")
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(project(":api"))
    implementation(libs.spring.boot)
}
