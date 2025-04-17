plugins {
    alias { libs.plugins.kotlin.jvm }
    alias { libs.plugins.google.ksp }
    id("project-conventions")
}

group = "pro.walkin.memo"
version = "0.0.1"

dependencies {
    implementation(project(":domain"))

    implementation(libs.jwtKt)
//    implementation("com.appstractive:ktor-server-auth-jwt:1.1.0")
//    implementation("com.appstractive:jwt-hmac-kt:1.1.0")

    implementation(libs.komapper.r2dbc)
    implementation(libs.komapper.r2dbc.pgsql)
    ksp(libs.komapper.processor)

}
