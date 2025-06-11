package dev.jamiecraane.ktf.users.model

import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val firebaseId: String,
    val roles: List<UserRole>,
)
