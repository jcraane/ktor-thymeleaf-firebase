# Ktor-Thymeleaf-Firebase Project Guidelines

This document provides essential information for developers working on this project.

## Build/Configuration Instructions

### Prerequisites
- JDK 21
- Docker and Docker Compose
- PostgreSQL client (optional, for direct database access)

### Environment Variables
The following environment variables need to be set:
- `ADMIN_USER_EMAIL` - Email for the admin user
- `ADMIN_PASSWORD` - Password for the admin user

### Database Setup
The project uses PostgreSQL, which is configured in the docker-compose.yml file:

```bash
# Start the PostgreSQL container
docker-compose up -d
```

This will start a PostgreSQL instance with:
- Database name: knowitall
- Username: postgres
- Password: postgres
- Port: 5438 (mapped from 5432)

### Building the Project
```bash
# Build the project
./gradlew build

# Run the application
./gradlew run
```

The application will be available at http://localhost:8080

## Testing Information

### Running Tests
The project uses JUnit for testing. Tests can be run with:

```bash
# Run all tests
./gradlew test

# Run a specific test
./gradlew test --tests "dev.jamiecraane.ktf.users.UserExposedServiceTest"
```

### Creating Tests
1. Create test classes in the `src/test/kotlin` directory, following the same package structure as the main code
2. Use the `kotlin.test` package for assertions
3. For testing suspend functions, use `kotlinx.coroutines.runBlocking`

### Example Test
Here's an example of a simple test for the UserExposedService:

```kotlin
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
```

Note: You'll need to import the appropriate classes and functions:
- `kotlin.test.Test`
- `kotlin.test.assertEquals`
- `kotlin.test.assertNotNull`
- `kotlinx.coroutines.runBlocking`

## Additional Development Information

### Project Structure
- `src/main/kotlin/dev/jamiecraane/ktf/` - Main application code
  - `configuration/` - Application configuration
  - `users/` - User-related functionality
  - `firebase/` - Firebase integration
  - `security/` - Security-related code
- `src/main/resources/` - Configuration files and templates
  - `db/migration/` - Flyway database migrations
  - `templates/` - Thymeleaf templates

### Key Technologies
- **Ktor**: Web framework for Kotlin
- **Exposed**: SQL framework for Kotlin
- **Koin**: Dependency injection
- **Firebase Admin SDK**: Firebase integration
- **Thymeleaf**: Templating engine
- **Flyway**: Database migrations
- **HikariCP**: Connection pooling
- **PostgreSQL**: Database

### Dependency Management
The project uses Gradle's version catalog (libs.versions.toml) for dependency management. To add or update dependencies:

1. Update the version in `gradle/libs.versions.toml`
2. Add the library definition if needed
3. Add it to a bundle if appropriate
4. Reference it in `build.gradle.kts`

### Code Style
- Follow Kotlin coding conventions
- Use suspend functions for asynchronous operations
- Use data classes for models
- Use interfaces for services to allow for dependency injection and testing
