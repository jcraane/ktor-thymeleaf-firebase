package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole

interface UserService {
    suspend fun retrieveUserByFirebaseId(firebaseUid: String): User?
    suspend fun retrieveUser(email: String): User?
    suspend fun createUser(
        firebaseId: String,
        email: String,
        firstName: String,
        lastName: String,
        roles: List<UserRole>
    ): User
}
