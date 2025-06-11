plugins {
    kotlin(libs.plugins.kotlin.get().pluginId).version(libs.versions.kotlin.get())
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktor)
}

group = "dev.jamiecraane.ktf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.ktorClient)
    implementation(libs.bundles.exposed)
    implementation(libs.bundles.koin)
    implementation(libs.firebase.admin)
    implementation(libs.logback.classic)
    implementation(libs.flyway)
    implementation(libs.hikariCP)
    implementation(libs.postgresql)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
