# 🛡️ Secure Spring Boot Authentication API

A Spring Boot application that implements a secure authentication system using JWT (JSON Web Tokens), rate limiting, email-based OTP (One-Time Password) for 2FA (Two-Factor Authentication), and role-based access control. It supports both REST API and basic form-based login.

---

## 🚀 Features

- 🔐 JWT-based authentication
- 📧 OTP via email for login verification
- 📊 Rate limiting on sensitive endpoints
- 👤 User registration & password reset
- 🔑 Role-based access (USER / ADMIN)
- 🖥️ REST API & form login support
- 🗑️ Admin user deletion (via API or form)

---

## 📦 Tech Stack

- Spring Boot
- Spring Security
- JWT (via custom filter)
- JavaMailSender (email OTP)
- Bucket4j (rate limiting)
- JPA + Hibernate
- H2 / MySQL (configurable)
- Thymeleaf (basic views)

---

## 📁 Project Structure

src/

├── controller/

│ ├── AuthApiController.java # Handles API login, register, OTP

│ ├── ApiUserController.java # Password reset, user info

│ ├── ApiAdminController.java # Admin user deletion

│ ├── AuthController.java # Web form login/register

│ └── UserController.java # Web admin delete

├── entity/

│ ├── User.java

│ ├── Role.java

│ ├── User_role.java

│ └── User_roleskey.java

│ └── OtpVerificationRequest.java #Aux class for request body in OTP login

│ └── PasswordResetRequest.java #Aux class for request body in password reset

│ └── SimpleUser.java #Aux class for login request

├── security/

│ ├── SecurityConfig.java # Two security filter chains, for JWT and form login

│ └── AuthTokenFilter.java # JWT filter

│ ├── EmailService.java #Method sendOtp, sends mail with otp code

│ └── RateLimiterService.java # For resetpwd and login, using IP

│ └── RoleService.java #Method to verify role

│ └── JwtUtil.java #Methods to generate token, getUsernameFromToken, validateToken 

│ └── EmailConfig.java #Configuration of JavaMailSender

│ └── CustomUserDetailsService.java #Retrieves UserDetails : loadUserByUsername

│ └── AuthEntryPointJwt.java

├── repository/

│ └── RoleRepository.java #Function to add a Role

│ └── UserRepository.java #Function to find user by username

├── UseraccessApplication.java

---

## 🛠️ Setup

### Configure application.properties

MySQL configuration

spring.datasource.url=jdbc:mysql://localhost:3306/userdb

spring.datasource.username=root

spring.datasource.password=admin

spring.datasource.driverClassName=com.mysql.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

Email service configuration

spring.mail.host=smtp.gmail.com

spring.mail.port=587

spring.mail.username=your_email@gmail.com

spring.mail.password=Application password generated on gmail

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true

Secret key for JWT

jwt.secret=YXjxxVVRZbVb47jWEdAVuZS15rQTziuo

### Build

mvn clean install


### Run the app

mvn spring-boot:run


## 🔐 API Overview

All /api/** endpoints require JWT unless otherwise noted.

### 🔑 /api/auth

| Endpoint          | Method | Description                        | Auth Required |
| ----------------- | ------ | ---------------------------------- | ------------- |
| `/login`          | POST   | Login with username/password → OTP | ❌             |
| `/verify-otp`     | POST   | Verify OTP and get JWT             | ❌             |
| `/register`       | POST   | Register a new USER                | ❌             |
| `/register/admin` | POST   | Register a new ADMIN               | ❌             |

Need to copy the JWT token retireved from /verify-otp

On Postman, Authorization, select Bearer token and paste it for methods that require Authorization

### 👤 /api/users

| Endpoint    | Method | Description       | Auth Required |
| ----------- | ------ | ----------------- | ------------- |
| `/{id}`     | GET    | Get own user info | ✅             |
| `/resetpwd` | POST   | Reset password    | ✅             |

### 🛠️ /api/admin

| Endpoint | Method | Description         | Role  | Auth Required |
| -------- | ------ | ------------------- | ----- | ------------- |
| `/{id}`  | DELETE | Delete user account | ADMIN | ✅             |


### 🌐 Web Access (Form Login)

GET /login – Form login

GET /register – Registration form

POST /register – Submits registration

GET /users/delete/ – Admin form to delete user

POST /users/delete/ – Submits delete request
