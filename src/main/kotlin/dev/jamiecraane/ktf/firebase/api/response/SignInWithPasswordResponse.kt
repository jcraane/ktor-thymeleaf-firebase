package dev.jamiecraane.ktf.firebase.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from signing in with email and password.
 *
 * @property idToken A Firebase Auth ID token for the authenticated user.
 * @property email The email for the authenticated user.
 * @property refreshToken A Firebase Auth refresh token for the authenticated user.
 * @property expiresIn The number of seconds in which the ID token expires.
 * @property localId The uid of the authenticated user.
 * @property registered Whether the email is for an existing account.
 * @see https://firebase.google.com/docs/reference/rest/auth#section-sign-in-email-password
 */
@Serializable
data class SignInWithPasswordResponse(
    @SerialName("idToken")
    val idToken: String,

    @SerialName("email")
    val email: String,

    @SerialName("refreshToken")
    val refreshToken: String,

    @SerialName("expiresIn")
    val expiresIn: String,

    @SerialName("localId")
    val localId: String,

    @SerialName("registered")
    val registered: Boolean
)
