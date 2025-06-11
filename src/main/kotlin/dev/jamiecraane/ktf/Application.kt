package dev.jamiecraane.ktf

import dev.jamiecraane.ktf.configuration.configureDI
import dev.jamiecraane.ktf.configuration.configureDatabase
import dev.jamiecraane.ktf.configuration.configureRouting
import dev.jamiecraane.ktf.configuration.configureTemplating
import dev.jamiecraane.ktf.users.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopPreparing
import io.ktor.server.cio.EngineMain
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val logger = LoggerFactory.getLogger("Application")

    monitor.subscribe(ApplicationStopPreparing) {
        logger.info("Application received event ApplicationStopPreparing")
    }

    val userService by inject<UserService>()

    configureDI()
    configureDatabase()
    configureRouting()
    configureTemplating(userService)
}
