package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole

class UserExposedService : UserService {
    override suspend fun retrieveUserByFirebaseId(firebaseUid: String): User? {
        return User(
            id = java.util.UUID.randomUUID(),
            email = "test@test.nl",
            firebaseId = firebaseUid,
            roles = listOf(UserRole.ADMIN)
        )
    }
}
