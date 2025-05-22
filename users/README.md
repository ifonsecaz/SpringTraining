# Users Management Service

A Spring Boot RESTful API for managing users, designed for easy integration and testing with environments (MySQL, H2).

---

## Table of Contents

- [Features](#features)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Profiles & Database Configuration](#profiles--database-configuration)
- [API Endpoints](#api-endpoints)
- [Sample Requests](#sample-requests)

---

## Features

- Create, update, delete, and fetch users
- Environment-specific configuration (e.g., MySQL for production and testing, H2 for developer)

---

## Setup

1. **Select the environment level**
    On resources, application-properties

2. **Configure the database**
    - For MySQL, edit `src/main/resources/application-{test/prod}.properties`:
      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/usermanagement
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      ```
    - For H2 (developer), see `application-dev.properties`.

3. **Build the project**
    ```sh
    ./mvnw clean install
    ```
    or on Windows:
    ```sh
    mvnw.cmd clean install
    ```

---

## Running the Application

Start the Spring Boot application:

```sh
./mvnw spring-boot:run
```
or
```sh
mvnw.cmd spring-boot:run
```

The API will be available at:  
`http://localhost:8083/` 
Port 8083 for testing
8082 for dev
8081 for prod
---

## Profiles & Database Configuration

- **Production and Testing:** Uses MySQL. Set in `application.properties`:
  ```
  spring.profiles.active=prod
  spring.profiles.active=test
  ```
- **Developer:** Uses H2 in-memory database. Set in `application-dev.properties` and activate with:
  ```sh
  ./mvnw dev -Dspring.profiles.active=dev
  ```

---

## API Endpoints

| Method | Endpoint         | Description         |
|--------|------------------|---------------------|
| POST   | `/users`         | Create a new user   |
| GET    | `/users`         | List all users      |
| GET    | `/users/{id}`    | Get user by ID      |
| PUT    | `/users/{id}`    | Update user by ID   |
| DELETE | `/users/{id}`    | Delete user by ID   |

---

## Sample Requests

### Create a New User

```http
POST /users
Content-Type: application/json

{
  "username": "jdoe",
  "email": "jdoe@example.com",
  "birth_date": "2000-12-22"
}
```

### Get All Users

```http
GET /users
```

### Update a User

```http
PUT /users/1
Content-Type: application/json

{
  "username": "john.doe",
  "email": "john.doe@example.com"
  "birth_date": "2000-12-22"
}
```

### Delete a User

```http
DELETE /users/1
```

---