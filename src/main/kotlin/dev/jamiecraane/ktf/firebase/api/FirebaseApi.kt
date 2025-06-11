package dev.jamiecraane.ktf.firebase.api

import dev.jamiecraane.ktf.firebase.api.request.SignInWithPasswordRequest
import dev.jamiecraane.ktf.firebase.api.response.SignInWithPasswordResponse

interface FirebaseApi {
    /**
     * Signs in a user with email and password.
     *
     * @param request The sign-in request containing email and password
     * @return The sign-in response containing idToken, refreshToken, and other user information
     * @see https://firebase.google.com/docs/reference/rest/auth#section-sign-in-email-password
     */
    suspend fun signInWithPassword(request: SignInWithPasswordRequest): SignInWithPasswordResponse
}
