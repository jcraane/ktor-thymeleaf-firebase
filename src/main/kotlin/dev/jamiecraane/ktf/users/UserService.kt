package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User

interface UserService {
    suspend fun retrieveUserByFirebaseId(firebaseUid: String): User?
}
