import io.freefair.gradle.plugins.lombok.LombokPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import java.util.concurrent.TimeUnit

class ProjectConventions : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(CodeFormatPlugin::class)
        target.plugins.apply(CustomRepositoryPlugin::class)
        target.plugins.apply(LombokPlugin::class)

        target.run {
            plugins.withType<JavaPlugin> {
                extensions.configure<JavaPluginExtension> {
                    dependencies{
                        add("implementation", "ca.solo-studios:slf4k:latest.release")
                    }
                    toolchain {
                        languageVersion.set(JavaLanguageVersion.of(21))
                    }
                }
            }

            plugins.withType<KotlinPlatformJvmPlugin>{
                extensions.configure<KotlinJvmProjectExtension> {
                    compilerOptions {
                        freeCompilerArgs.addAll("-Xjsr305=strict")
                    }
                }
            }



            tasks.withType<Test> {
                useJUnitPlatform()
                maxHeapSize = "1024M"
            }

            plugins.withType(JavaPlugin::class) {
                configurations.all {
                    exclude("ch.qos.logback", "logback-classic")
                    exclude("ch.qos.logback", "logback-core")
                    exclude("org.springframework.boot", "spring-boot-starter-logging")
                    resolutionStrategy {
                        cacheChangingModulesFor(0, TimeUnit.SECONDS)
                    }
                }
            }

            // 解决gradle出现Entry classpath.index is a duplicate的问题
            tasks.withType<Jar> {
                duplicatesStrategy = DuplicatesStrategy.INCLUDE
            }

            tasks.withType<JavaCompile> {
                options.encoding = "UTF-8"
                options.compilerArgs.add("-parameters")
            }
        }

    }
}
