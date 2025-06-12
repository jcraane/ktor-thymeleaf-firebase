# Ktor-Thymeleaf-Firebase Authentication Example

This project demonstrates how to implement Firebase Authentication with Ktor and Thymeleaf for server-side rendering. It accompanies the blog post: [Firebase Authentication with Ktor and Thymeleaf](https://jamiecraane.dev/2025/06/12/firebase_auth_ktor_thymeleaf/).

## Overview

This application showcases:
- Firebase Authentication integration with Ktor
- Server-side rendering with Thymeleaf templates
- Role-based access control
- PostgreSQL database with Exposed ORM
- Flyway database migrations

## Prerequisites

- JDK 21
- Docker and Docker Compose
- Firebase project with Authentication enabled

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/username/ktor-thymeleaf-firebase.git
cd ktor-thymeleaf-firebase
```

### 2. Configure Firebase

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Enable Email/Password authentication
3. Download your Firebase Admin SDK service account key (JSON file):
   - Go to Project Settings (gear icon in the top left)
   - Navigate to the "Service accounts" tab
   - Click "Generate new private key"
   - Save the JSON file in a secure location
4. Obtain your Firebase Web API Key:
   - In Project Settings, go to the "General" tab
   - Scroll down to the "Your apps" section
   - If you don't have a web app, click "Add app" and select the web platform (</>) 
   - After creating the web app or if one already exists, you'll see the "Web API Key" in the SDK setup and configuration
   - Copy this key for use in your environment variables

### 3. Set Environment Variables

Set the following environment variables:

```bash
export ADMIN_USER_EMAIL=your-admin-email@example.com
export ADMIN_PASSWORD=your-secure-password
export FIREBASE_CONFIG=Contents of the firebase-service-account.json
export FIREBASE_WEB_API_KEY=Firebase web api key
```

### 4. Start the Database

```bash
docker-compose up -d
```

This will start a PostgreSQL instance with:
- Database name: ktf
- Username: postgres
- Password: postgres
- Port: 5438 (mapped from 5432)

### 5. Build and Run the Application

```bash
./gradlew build
./gradlew run
```

The application will be available at http://localhost:8089

## Project Structure

- `src/main/kotlin/dev/jamiecraane/ktf/` - Main application code
  - `configuration/` - Application configuration
  - `users/` - User-related functionality
  - `firebase/` - Firebase integration
  - `security/` - Security-related code
- `src/main/resources/` - Configuration files and templates
  - `db/migration/` - Flyway database migrations
  - `templates/` - Thymeleaf templates

## Key Features

### Firebase Authentication

The application uses Firebase Authentication for user management. It creates a session cookie after successful authentication and verifies it on subsequent requests.

### Role-Based Access Control

Users can have different roles (e.g., ADMIN), which are stored in the database and used for authorization.

### Admin Dashboard

An admin dashboard is available at `/admin/dashboard`, protected by authentication and role-based access control.

## Technologies Used

- **Ktor**: Web framework for Kotlin
- **Exposed**: SQL framework for Kotlin
- **Koin**: Dependency injection
- **Firebase Admin SDK**: Firebase integration
- **Thymeleaf**: Templating engine
- **Flyway**: Database migrations
- **HikariCP**: Connection pooling
- **PostgreSQL**: Database

## Development

### Running in Development Mode

The application uses different template resolvers for development and production modes. In development mode, templates are loaded from the filesystem for hot-reloading.

### Database Migrations

Database migrations are managed by Flyway and automatically applied on startup.

## License

[MIT License](LICENSE)

## Further Reading

For more details on how this project works, check out the accompanying blog post: [Firebase Authentication with Ktor and Thymeleaf](https://jamiecraane.dev/2025/06/12/firebase_auth_ktor_thymeleaf/).
