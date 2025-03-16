plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://repo.huaweicloud.com/repository/maven/")
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.lombok.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.spotless.gradle)
}

gradlePlugin {
    plugins.create("ProjectConventions") {
        id = "project-conventions"
        implementationClass = "ProjectConventions"
    }
    plugins.register("CodeFormatPlugin"){
        id="code-format"
        implementationClass = "CodeFormatPlugin"
    }
}
