# Spring JWT Authentication

Spring Boot backend for managing authentication using JWT.

---

## Prerequisites

- Java 21
- Maven
- PostgreSQL

---

## Configuration

### 1️⃣ Database

Create a PostgreSQL database and user:

CREATE DATABASE springjwtauth;
CREATE USER springuser WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE springjwtauth TO springuser;

### 2️⃣ Create your `application.properties` in resources folder

 ```
spring.datasource.url=jdbc:postgresql://localhost:5432/springjwtauth
spring.datasource.username=springuser
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=ChangeThisSecretToSomethingSafeAndLong123!
jwt.expiration=3600000
 ```
_you can check check resources/application-example.properties_

---

## Running the project

mvn spring-boot:run

The application runs at: http://localhost:8080

---

## Available Endpoints

### 1️⃣ Register

- **URL** : POST /auth/register  
- **Headers** : Content-Type: application/json  
- **Body**:

{
  "email": "test@mail.com",
  "password": "password123"
}

- **Response**:

{
  "id": 1,
  "email": "test@mail.com",
  "password": "$2a$10$..."   // bcrypt hash
}

---

### 2️⃣ Login

- **URL** : POST /auth/login  
- **Headers** : Content-Type: application/json  
- **Body**:

{
  "email": "test@mail.com",
  "password": "password123"
}

- **Response**:

{
  "token": "<JWT_TOKEN>"
}

---

### 3️⃣ Protected Route (Example)

- **URL** : GET /api/users/test  
- **Headers** :  

Authorization: Bearer <JWT_TOKEN>

- **Response**:

JWT OK => authenticated

- **Without token** : 403
- **Invalid token** : 401

---

## Security

- JWT token expires after 1 hour (`jwt.expiration` in ms).  
- `/auth/**` routes are public.  
- All other routes require a valid JWT token.  

---
