package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole
import java.util.UUID

class UserExposedService : UserService {
    override suspend fun retrieveUserByFirebaseId(firebaseUid: String): User? {
        return User(
            id = UUID.randomUUID(),
            email = "test@test.nl",
            firebaseId = firebaseUid,
            roles = listOf(UserRole.ADMIN)
        )
    }

    override suspend fun retrieveUser(email: String): User? {
        // This is a simple implementation for testing purposes
        return User(
            id = UUID.randomUUID(),
            email = email,
            firebaseId = "test-firebase-id",
            roles = listOf(UserRole.USER)
        )
    }

    override suspend fun createUser(
        firebaseId: String,
        email: String,
        firstName: String,
        lastName: String,
        roles: List<UserRole>
    ): User {
        // This is a simple implementation for testing purposes
        return User(
            id = UUID.randomUUID(),
            email = email,
            firebaseId = firebaseId,
            roles = roles
        )
    }
}
