package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class UserExposedService : UserService {
    override suspend fun retrieveUserByFirebaseId(firebaseUid: String): User? {
        return newSuspendedTransaction {
            UsersTable.selectAll()
                .where { UsersTable.firebaseId eq firebaseUid }
                .mapNotNull { UsersTable.createUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun retrieveUser(email: String): User? {
        return newSuspendedTransaction {
            UsersTable.selectAll()
                .where { UsersTable.email eq email }
                .mapNotNull { UsersTable.createUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun createUser(
        firebaseId: String,
        email: String,
        roles: List<UserRole>,
    ): User {
        val user = User(
            id = UUID.randomUUID(),
            email = email,
            firebaseId = firebaseId,
            roles = roles
        )

        newSuspendedTransaction {
            UsersTable.insert {
                it[id] = user.id
                it[UsersTable.firebaseId] = user.firebaseId
                it[UsersTable.email] = user.email.lowercase()
                it[UsersTable.roles] = user.roles.map { it.code }
            }
        }

        return user
    }
}
