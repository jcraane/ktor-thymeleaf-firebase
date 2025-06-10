package dev.jamiecraane.ktf

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopPreparing
import io.ktor.server.cio.EngineMain
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val logger = LoggerFactory.getLogger("Application")

    monitor.subscribe(ApplicationStopPreparing) {
        logger.info("Application received event ApplicationStopPreparing")
    }
}
