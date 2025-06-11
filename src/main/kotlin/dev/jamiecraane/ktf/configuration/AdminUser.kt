package dev.jamiecraane.ktf.configuration

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import dev.jamiecraane.ktf.users.UserService
import dev.jamiecraane.ktf.users.model.UserRole
import io.ktor.server.application.Application
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import org.slf4j.Logger

fun Application.createAdminUserOfNotExists(logger: org.slf4j.Logger) {
    val userService by inject<UserService>()
    val firebaseAuth by inject<FirebaseAuth>()

    runCatching {
        launch {
            createAdminUserOfNotExists(
                firebaseAuth = firebaseAuth,
                userService = userService,
                email = System.getenv("ADMIN_USER_EMAIL"),
                password = System.getenv("ADMIN_PASSWORD"),
                logger = logger,
            )
        }
    }.onFailure {
        logger.error("Failed to configure admin user", it)
    }
}

private suspend fun createAdminUserOfNotExists(
    firebaseAuth: FirebaseAuth,
    userService: UserService,
    email: String,
    password: String,
    logger: Logger,
) {
    if (userService.retrieveUser(email) == null) {
        logger.info("Creating admin user")

        firebaseAuth.createUser(
            UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(true)
        )?.also { userRecord ->
            userService.createUser(
                firebaseId = userRecord.uid,
                email = email,
                firstName = "admin",
                lastName = "admin",
                roles = listOf(UserRole.ADMIN)
            )
        }
    } else {
        logger.info("Admin user already exists")
    }
}
