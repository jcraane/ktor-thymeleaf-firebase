package dev.jamiecraane.ktf.di

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import dev.jamiecraane.ktf.firebase.api.FirebaseApi
import dev.jamiecraane.ktf.firebase.api.RealFirebaseApi
import dev.jamiecraane.ktf.users.UserExposedService
import dev.jamiecraane.ktf.users.UserService
import io.ktor.client.HttpClient
import org.koin.dsl.module
import java.io.ByteArrayInputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun mainModule() = module {
    single<UserService> {
        UserExposedService()
    }

    single {
        val decoded = Base64.decode(System.getenv("FIREBASE_CONFIG"))

        val options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(
                    ByteArrayInputStream(String(decoded).toByteArray(Charsets.UTF_8))
                )
            )
            .build()

        FirebaseApp.initializeApp(options)
        FirebaseAuth.getInstance()
    }

    single<FirebaseApi> {
        RealFirebaseApi(
            client = HttpClient(),
            apiKey = System.getenv("FIREBASE_WEB_API_KEY")
        )
    }
}
