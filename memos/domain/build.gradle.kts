plugins {
    alias { libs.plugins.kotlin.jvm }
    alias(libs.plugins.kotlin.serialization)
    id("project-conventions")
}

dependencies{
    api(libs.shared.api)
    api(libs.kotlinx.datetime)
    api(libs.kotlinx.serialization.json)

}

kotlin{
    compilerOptions{
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}


