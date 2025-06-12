
plugins {
//    id("module") apply false
//    alias { libs.plugins.kotlin.jvm } apply false
//    alias(libs.plugins.kotlin.serialization) apply false
//    alias { libs.plugins.detekt }
}

allprojects {
//    apply(plugin = "io.gitlab.arturbosch.detekt")

//    detekt {
//        autoCorrect = true
//        buildUponDefaultConfig = true
//        baseline = file("$rootDir/config/detekt/baseline.xml")
//    }

    dependencies {
//        detekt(project(":detekt-cli"))
//        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
    }

//    tasks.withType<Detekt>().configureEach {
//        jvmTarget = "1.8"
//        reports {
//            xml.required = true
//            html.required = true
//            sarif.required = true
//            md.required = true
//        }
//        basePath = rootDir.absolutePath
//    }
//    detektReportMergeSarif {
//        input.from(tasks.withType<Detekt>().map { it.reports.sarif.outputLocation })
//    }
//    tasks.withType<DetektCreateBaselineTask>().configureEach {
//        jvmTarget = "1.8"
//    }
}
