package dev.jamiecraane.ktf.configuration

import com.google.firebase.auth.FirebaseAuth
import dev.jamiecraane.ktf.security.ADMIN_AUTH
import dev.jamiecraane.ktf.users.model.UserRole
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.session
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val firebaseAuth by inject<FirebaseAuth>()

    install(Sessions) {
        cookie<String>("session")
    }

    install(Authentication) {
        session<String>(ADMIN_AUTH) {
            validate { sessionCookie ->
                try {
                    // Verify the session cookie
                    val decodedToken = firebaseAuth.verifySessionCookie(sessionCookie, true)
                    val roles = decodedToken.claims["roles"] as? String

                    if (roles == UserRole.ADMIN.code) {
                        UserIdPrincipal(decodedToken.uid)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}
