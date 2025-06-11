package dev.jamiecraane.ktf.configuration

import dev.jamiecraane.ktf.DatabaseFactory

fun configureDatabase() {
    DatabaseFactory.init(
        dbUrl = "jdbc:postgresql://localhost:5438/ktf",
        dbUser = "postgres",
        dbPassword = "postgres",
    )
}
