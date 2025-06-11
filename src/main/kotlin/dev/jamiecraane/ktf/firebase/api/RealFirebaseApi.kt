package dev.jamiecraane.ktf.firebase.api

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import dev.jamiecraane.ktf.firebase.api.request.SignInWithPasswordRequest
import dev.jamiecraane.ktf.firebase.api.response.FirebaseErrorResponse
import dev.jamiecraane.ktf.firebase.api.response.SignInWithPasswordResponse
import org.slf4j.LoggerFactory

/**
 * Implementation of the Firebase Authentication API.
 *
 * @property client The HTTP client used to make requests.
 * @property apiKey The Firebase Web API key.
 */
class RealFirebaseApi(
    private val client: HttpClient,
    private val apiKey: String,
) : FirebaseApi {
    override suspend fun signInWithPassword(request: SignInWithPasswordRequest): SignInWithPasswordResponse {
        val response =
            client.post("$SIGN_IN_WITH_PASSWORD_ENDPOINT?key=$apiKey") {
                contentType(ContentType.Application.Json)
                val json = defaultJson.encodeToString(SignInWithPasswordRequest.serializer(), request)
                logger.debug("Request: {}", json)
                setBody(json)
            }

        val body = response.bodyAsText()
        logger.debug("Response: {}", body)

        if (response.status.isSuccess().not()) {
            val errorResponse = parseErrorResponse(body)
            throw RuntimeException("Firebase authentication failed: ${errorResponse?.error?.message ?: "Unknown error"}")
        }

        return defaultJson.decodeFromString(
            SignInWithPasswordResponse.serializer(),
            body
        )
    }

    private fun parseErrorResponse(json: String): FirebaseErrorResponse? = try {
        defaultJson.decodeFromString(FirebaseErrorResponse.serializer(), json)
    } catch (e: Exception) {
        logger.error("Failed to parse error response: {}", json, e)
        null
    }

    companion object {
        private const val SIGN_IN_WITH_PASSWORD_ENDPOINT =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword"
        private val logger = LoggerFactory.getLogger(RealFirebaseApi::class.java)

        private val defaultJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }
}
