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
    implementation(libs.bundles.exposed)
    implementation(libs.bundles.koin)
    implementation(libs.firebase.admin)
    implementation(libs.flyway)
    implementation(libs.hikariCP)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
