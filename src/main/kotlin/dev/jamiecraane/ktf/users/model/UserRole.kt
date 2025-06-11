package dev.jamiecraane.ktf.users.model

import kotlinx.serialization.Serializable
import kotlin.collections.find
import kotlin.text.equals

@Serializable
enum class UserRole(val code: String) {
    USER("user"),
    ADMIN("admin");

    companion object {
        fun fromCode(code: String): UserRole {
            return entries.find { it.name.equals(code, ignoreCase = true) } ?: USER
        }
    }
}
