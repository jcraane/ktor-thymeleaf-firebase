plugins {
    kotlin(libs.plugins.kotlin.get().pluginId).version(libs.versions.kotlin.get())
}

group = "dev.jamiecraane.ktf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.firebase.admin)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
