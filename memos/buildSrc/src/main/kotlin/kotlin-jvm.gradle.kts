// The code in this file is a convention plugin - a Gradle mechanism for sharing reusable build logic.
// `buildSrc` is a Gradle-recognized directory and every plugin there will be easily available in the rest of the build.
package buildsrc.convention

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

kotlin {
    // 使用特定的 Java 版本可以更轻松地在不同环境中工作。
    jvmToolchain(22)
}

val versionCatalog = versionCatalogs.named("libs")
val detektVersion = versionCatalog.findVersion("detekt-version").get().requiredVersion

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detektVersion}")
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    baseline = rootProject.file("config/detekt/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "22"
    reports {
        xml.required = true
        html.required = true
        sarif.required = true
        md.required = true
    }
    basePath = rootProject.path
}


tasks.test {
    useJUnitPlatform()
}
//tasks.withType<Test>().configureEach {
//    // Log information about all test results, not only the failed ones.
//    testLogging {
//        events(
//            TestLogEvent.FAILED,
//            TestLogEvent.PASSED,
//            TestLogEvent.SKIPPED
//        )
//    }
//}
