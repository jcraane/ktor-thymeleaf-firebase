package dev.jamiecraane.ktf.configuration

import dev.jamiecraane.ktf.di.mainModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger(Level.INFO)
        modules(mainModule())
    }
}
