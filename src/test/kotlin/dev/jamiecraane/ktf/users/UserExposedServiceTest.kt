package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserExposedServiceTest {

    @Test
    fun testRetrieveUserByFirebaseId() = runBlocking {
        // Arrange
        val service = UserExposedService()
        val firebaseId = "test-firebase-id"

        // Act
        val user = service.retrieveUserByFirebaseId(firebaseId)

        // Assert
        assertNotNull(user)
        assertEquals(firebaseId, user.firebaseId)
        assertEquals("test@test.nl", user.email)
        assertEquals(1, user.roles.size)
        assertEquals(UserRole.ADMIN, user.roles[0])
    }
}
