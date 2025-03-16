import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import java.net.URI

class CustomRepositoryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.repositories {
            maven {
                url = URI("https://repo.huaweicloud.com/repository/maven/")
            }
            maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            mavenCentral()
        }
    }
}
