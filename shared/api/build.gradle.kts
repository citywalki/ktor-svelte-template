plugins {
    kotlin("jvm")
    alias { libs.plugins.kotlin.serialization }
    id("project-conventions")
}

dependencies {
    api(libs.kotlinx.datetime)
    api(libs.kotlinx.serialization.json)
}
