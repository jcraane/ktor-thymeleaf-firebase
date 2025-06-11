package dev.jamiecraane.ktf.configuration

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SessionCookieOptions
import dev.jamiecraane.ktf.firebase.api.FirebaseApi
import dev.jamiecraane.ktf.firebase.api.request.SignInWithPasswordRequest
import dev.jamiecraane.ktf.firebase.api.response.SignInWithPasswordResponse
import dev.jamiecraane.ktf.security.ROLES_CLAIM
import dev.jamiecraane.ktf.users.UserService
import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sessions.sessions
import kotlinx.serialization.Serializable
import java.util.concurrent.TimeUnit

/**
 * These routes are specific for handling the login/logout for the server-side admin dashboard. Sessions
 * for the dashboard are managed using Firebase session cookies.
 */
fun Route.adminRoutes(
    firebaseAuth: FirebaseAuth,
    userService: UserService,
    firebaseApi: FirebaseApi,
) {
    route("/admin") {
        post("/login") {
            val parameters = call.receiveParameters()
            val email = parameters["email"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse("Email is required")
            )
            val password = parameters["password"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse("Password is required")
            )


            runCatching {
                val signInResponse = signInWithEmailAndPassword(email, password, firebaseApi)
                val firebaseUid = signInResponse.localId

                val user = getAuthenticatedAdmin(userService, firebaseUid)
                setCustomClaimsForUser(user, firebaseAuth, firebaseUid)

                val sessionCookie = generateSessionCookie(firebaseAuth, signInResponse)
                call.sessions.set("session", sessionCookie)

                call.respondRedirect("/admin/dashboard")
            }.onFailure {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse("Invalid credentials")
                )
            }
        }

        post("/logout") {
            runCatching {
                call.sessions.clear("session")
                call.respondRedirect("/admin/login")
            }.onFailure {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse("Logout failed: ${it.message}")
                )
            }
        }
    }
}

private fun generateSessionCookie(
    firebaseAuth: FirebaseAuth,
    signInResponse: SignInWithPasswordResponse,
): String? {
    val expiresIn: Long = TimeUnit.DAYS.toMillis(5)
    val options = SessionCookieOptions.builder()
        .setExpiresIn(expiresIn)
        .build()

    val sessionCookie = firebaseAuth.createSessionCookie(signInResponse.idToken, options)
    return sessionCookie
}

private fun setCustomClaimsForUser(
    user: User,
    firebaseAuth: FirebaseAuth,
    firebaseUid: String,
) {
    val customClaims = mapOf(
        ROLES_CLAIM to user.roles.joinToString(",") { it.code }
    )

    firebaseAuth.setCustomUserClaims(firebaseUid, customClaims)
}

private suspend fun getAuthenticatedAdmin(
    userService: UserService,
    firebaseUid: String,
): User {
    val user = userService.retrieveUserByFirebaseId(firebaseUid)
    require(user != null) { "User not found" }

    val isAdmin = user.roles.contains(UserRole.ADMIN)
    require(isAdmin) { "User is not an admin" }

    return user
}

private suspend fun signInWithEmailAndPassword(
    email: String,
    password: String,
    firebaseApi: FirebaseApi,
): SignInWithPasswordResponse {
    val signInRequest = SignInWithPasswordRequest(
        email = email,
        password = password,
        returnSecureToken = true
    )

    val signInResponse = firebaseApi.signInWithPassword(signInRequest)
    return signInResponse
}

@Serializable
data class ErrorResponse(
    val message: String,
)
