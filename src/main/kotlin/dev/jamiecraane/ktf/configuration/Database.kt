package dev.jamiecraane.ktf.configuration

import dev.jamiecraane.ktf.DatabaseFactory
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline

fun Application.configureDatabase() {
    DatabaseFactory.init(
        dbUrl = "jdbc:postgresql://localhost:5438/knowitall",
        dbUser = "postgres",
        dbPassword = "postgres",
    )
}
