package dev.jamiecraane.ktf.configuration

import com.google.firebase.auth.FirebaseAuth
import dev.jamiecraane.ktf.firebase.api.FirebaseApi
import dev.jamiecraane.ktf.users.UserService
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val firebaseApi by inject<FirebaseApi>()
    val userService by inject<UserService>()
    val firebaseAuth by inject<FirebaseAuth>()

    routing {
        adminRoutes(
            firebaseAuth = firebaseAuth,
            userService = userService,
            firebaseApi = firebaseApi,
        )
    }
}
