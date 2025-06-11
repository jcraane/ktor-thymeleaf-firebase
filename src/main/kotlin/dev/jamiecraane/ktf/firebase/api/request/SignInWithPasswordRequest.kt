package dev.jamiecraane.ktf.firebase.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request for signing in with email and password.
 *
 * @property email The email of the user.
 * @property password The password of the user.
 * @property returnSecureToken Whether to return an ID and refresh token. Should always be true.
 * @see https://firebase.google.com/docs/reference/rest/auth#section-sign-in-email-password
 */
@Serializable
data class SignInWithPasswordRequest(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("returnSecureToken")
    val returnSecureToken: Boolean = true
)
