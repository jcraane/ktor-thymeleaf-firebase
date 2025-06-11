package dev.jamiecraane.ktf

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transactionManager
import java.sql.Connection
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object DatabaseFactory {
    fun init(
        dbUrl: String,
        dbUser: String,
        dbPassword: String,
    ) {
        val database = Database.Companion.connect(hikari(dbUrl, dbUser, dbPassword))
        database.transactionManager.defaultIsolationLevel = Connection.TRANSACTION_READ_COMMITTED

        val flyway = Flyway.configure()
            .cleanDisabled(true)
            .cleanOnValidationError(false)
            .dataSource(dbUrl, dbUser, dbPassword).load()

        flyway.migrate()
    }

    private fun hikari(
        dbUrl: String,
        dbUser: String,
        dbPassword: String,
    ): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 15
        config.connectionTimeout = 30.seconds.inWholeMilliseconds
        config.idleTimeout = 10.minutes.inWholeMilliseconds
        config.maxLifetime = 30.minutes.inWholeMilliseconds
        config.isAutoCommit = false
        config.validate()
        return HikariDataSource(config)
    }
}
