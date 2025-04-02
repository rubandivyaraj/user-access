# Spring Boot JWT Authentication and Authorization

## Overview

This project implements a Spring Boot 3 application that uses JWT (JSON Web Tokens) for secure authentication and
authorization with Spring Security 6. The application provides a RESTful API for user registration, login, and access to
protected resources using JWT tokens, which is a widely adopted and industry-standard approach.

## Features

- User registration and login functionality (completed).
- JWT-based authentication (completed).
- Secure RESTful API endpoints. (Completed)
- Common response for all APIs (Completed)
- JWT-based authorization (pending)
- Dynamic secret key to generate JWT token. (Completed)
- Global exception handling (Completed)
- Spring validation implementation (Completed)
- MariaDB Database for data storage. (Completed)

## Requirements

- Java 17 or higher
- Spring Boot 3.x
- Spring Security 6.x
- MariaDB Database [As per your need]
- Maven (for dependency management)

### SQL Details (MariaDB / MySQL)
This query is based on the `MariaDB`. Alter this query based on your Databases.

- **USERS** table
  ```
  CREATE TABLE `users` (
  user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(60) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `role` VARCHAR(60) NOT NULL DEFAULT 'user',
  `created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_enabled` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email` (`email`),
  UNIQUE INDEX `user_name` (`username`)
  );
  ```

### API Endpoints

**Sign-up / Register a new user:**

- **POST**    /user/v1/auth/signup
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

**Login with existing user:**

- **POST** /user/v1/auth/login

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


**Validating JWT Token:**

- **GET** /user/v1/auth/validate

  - Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJydWJhbiIsImlhdCI6MTc0MjE0NjIzNywiZXhwIjoxNzQyMTQ5ODM3fQ.WemxLh7PnM9w-r111osH9EOlXWkTViUft4EShSBRqMw
  ```

  - Response:
  ```
    {
      "sub": "ruban",
      "iat": 1743615151,
      "exp": 1743615511
    }
  ```


**Logout / Expire JWT Token:**

- **GET** /user/v1/auth/validate

  - Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJydWJhbiIsImlhdCI6MTc0MjE0NjIzNywiZXhwIjoxNzQyMTQ5ODM3fQ.WemxLh7PnM9w-r111osH9EOlXWkTViUft4EShSBRqMw
  ```

  - Response:
  ```
  {
    "status": 202,
    "message": "Accepted",
    "data": "Successfully logged out...",
    "timestamp": "2025-04-02T23:02:49.0005211"
  }
  ```

**Access a protected resource:**
  - **GET** /api/product [This is for the sample to access API key with bearer token]
    - Headers:
    ```
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJydWJhbiIsImlhdCI6MTc0MjE0NjIzNywiZXhwIjoxNzQyMTQ5ODM3fQ.WemxLh7PnM9w-r111osH9EOlXWkTViUft4EShSBRqMw
    ```
  - Response:
    ```
    {
    "status": 202,
    "message": "Success",
    "data": [
        "Apple",
        "Bat",
        "Computer"
    ],
    "timestamp": "2025-04-02T23:04:12.3608203"
    }
    ```

### Dynamic Secret Key Generation

The secret key for JWT is generated dynamically using a key generator. This key will change every time the application
is restarted, enhancing security.

## Collaboration

For collaboration, you can contact me through my LinkedIn profile:
Ruban Divyaraj - [LinkedIn Profile](https://www.linkedin.com/in/rubandivyaraj)

