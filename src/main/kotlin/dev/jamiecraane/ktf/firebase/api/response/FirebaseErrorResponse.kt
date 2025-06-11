package dev.jamiecraane.ktf.firebase.api.response

import kotlinx.serialization.Serializable

/**
 * Error response from Firebase Authentication API.
 *
 * @property error The error details.
 * @see https://firebase.google.com/docs/reference/rest/auth#section-error-response
 */
@Serializable
data class FirebaseErrorResponse(
    val error: FirebaseErrorDetails
)

/**
 * Details of a Firebase Authentication error.
 *
 * @property message The error message.
 * @property code The error code.
 * @see https://firebase.google.com/docs/reference/rest/auth#section-error-response
 */
@Serializable
data class FirebaseErrorDetails(
    val message: String,
    val code: Int
)
