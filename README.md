# Spring Boot JWT Authentication

## Overview
This project is a Spring Boot 3 application that implements JWT (JSON Web Tokens) for secure authentication and authorization using Spring Security 6. The application provides a RESTful API that allows users to register, log in, and access protected resources using JWT tokens. The secret key for JWT is dynamically generated using a key generator, ensuring a new key is created each time the application restarts. The application uses H2 Database for data storage.

## Features
- User registration and login functionality (completed).
- JWT-based authentication and authorization (completed).
- Dynamic secret key generation on application startup.
- Secure RESTful API endpoints.
- Role-based access control (planned).
- Logout functionality (pending).
- Token expiration handling (pending).
- Refresh token mechanism (optional, pending).
- H2 Database for data storage.

## Requirements
- Java 17 or higher
- Spring Boot 3.x
- Spring Security 6.x
- H2 Database
- Maven (for dependency management)

### API Endpoints

**Register a new user:**

**POST**    /user/signup
- Request body:
    ```
    {
        "userName": "ruban",
        "password": "ruban",
        "email": "ruban@mail.com"
	}
    ```
- Response:
    ```
    {
        "status": 201,
        "message": "Created",
        "data": "ruban",
        "timestamp": "2025-03-01T22:59:21.4947822"
    }
    ```

**Login:**

- **POST** /user/login

- Request body:
    ```
    {
        "userName": "ruban",
        "password": "ruban"
	} 
    ```

- Response:
    ```
    {
        "status": 202,
        "message": "Accepted",
        "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJydWJhbiIsImlhdCI6MTc0MjE0NjIzNywiZXhwIjoxNzQyMTQ5ODM3fQ.WemxLh7PnM9w-r111osH9EOlXWkTViUft4EShSBRqMw",
        "timestamp": "2025-03-16T23:00:37.6739589"
    }
    ```

- **Access a protected resource:**
  - **GET** /api/product
  - Headers:

  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJydWJhbiIsImlhdCI6MTc0MjE0NjIzNywiZXhwIjoxNzQyMTQ5ODM3fQ.WemxLh7PnM9w-r111osH9EOlXWkTViUft4EShSBRqMw
  ```
### Dynamic Secret Key Generation

The secret key for JWT is generated dynamically using a key generator. This key will change every time the application is restarted, enhancing security.

## Pending Features

- **Logout Functionality**: Implement a mechanism to invalidate the JWT token on logout.
- **Token Expiration Handling**: Implement logic to handle token expiration and refresh tokens if necessary.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please open an issue or submit a pull request.

## Author
Ruban Divyaraj - [LinkedIn Profile](https://www.linkedin.com/in/rubandivyaraj)

