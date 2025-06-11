package dev.jamiecraane.ktf.users

import dev.jamiecraane.ktf.users.model.User
import dev.jamiecraane.ktf.users.model.UserRole
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow

object UsersTable : UUIDTable("users") {
    val firebaseId = varchar("firebase_id", 36)
    val email = varchar("email", 150)
    val roles = array<String>("roles")

    fun createUser(row: ResultRow): User = User(
        id = row[id].value,
        firebaseId = row[firebaseId],
        email = row[email],
        roles = row[roles].map { UserRole.fromCode(it) },
    )
}
