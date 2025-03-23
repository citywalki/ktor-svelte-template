plugins {
    alias { libs.plugins.kotlin.jvm } apply false
}

allprojects {
    group = "com.github.walkin.memos"
    version = "0.0.1-SNAPSHOT"
}

tasks.register("spotlessApply"){
    dependsOn(gradle.includedBuilds.filter { it.name != "gradle-plugins" }.map { it.task(":spotlessApply") })

}
