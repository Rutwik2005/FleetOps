# FleetOps

FleetOps is a modular logistics and fleet management platform built with Java Spring Boot. It is designed to simplify transportation operations by managing fleets, shipments, dispatchers, drivers, notifications, and administrative workflows through a scalable REST API architecture.

The project follows clean architecture principles, emphasizing modular design, maintainability, and production-ready backend practices.

---

## Features

### User Management
- User registration
- Role-based users
- Email uniqueness validation
- DTO-based API design
- Global exception handling
- Input validation

### Fleet Management *(In Progress)*
- Vehicle registration
- Vehicle availability tracking
- Vehicle maintenance records
- Driver assignment

### Shipment Management *(Planned)*
- Shipment creation
- Shipment status tracking
- Route assignment
- Delivery history

### Dispatch Module *(Planned)*
- Shipment assignment
- Driver assignment
- Route scheduling

### Notification Module *(Planned)*
- Email notifications
- Shipment alerts
- Delivery updates

### Authentication *(Upcoming)*
- JWT Authentication
- Spring Security
- Role-Based Authorization
- Secure REST APIs

---

# Technology Stack

## Backend

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate
- Maven

## Database

- PostgreSQL

## Validation

- Jakarta Validation

## Utilities

- Lombok

## API Testing

- Postman

## Version Control

- Git
- GitHub

---

# Project Structure

```
src/main/java
│
├── auth
├── audit
├── common
├── dispatch
├── fleet
├── notification
├── shipment
│
└── users
    ├── controller
    ├── dto
    ├── entity
    ├── exception
    ├── mapper
    ├── repository
    └── service
```

---

# Architecture

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
```

The project follows a layered architecture with clear separation of responsibilities.

- Controllers expose REST APIs.
- Services contain business logic.
- Repositories interact with the database.
- DTOs isolate API contracts.
- Mappers convert DTOs to entities and vice versa.

---

# Current Progress

## Completed

- Spring Boot project setup
- PostgreSQL integration
- Maven configuration
- User Entity
- User Repository
- DTO layer
- Mapper layer
- Service layer
- REST Controller
- Global Exception Handling
- Email uniqueness validation
- Bean Validation
- GitHub repository setup

## In Progress

- JWT Authentication
- Login API
- Password Encryption
- User Retrieval APIs

## Planned

- Fleet Module
- Shipment Module
- Dispatch Module
- Notification Module
- Audit Logs
- Dashboard APIs
- Docker Support
- CI/CD Pipeline
- Unit & Integration Testing

---

# API Endpoints

## User APIs

### Create User

```
POST /api/users
```

Example Request

```json
{
  "name": "Rutwik Vaidya",
  "email": "rutwik@example.com",
  "password": "Password@123"
}
```

Example Response

```json
{
  "id": 1,
  "name": "Rutwik Vaidya",
  "email": "rutwik@example.com",
  "role": "DISPATCHER"
}
```

---

# Validation

The application validates incoming requests using Jakarta Validation.

Supported validations include:

- Required fields
- Email format validation
- Password length validation
- Duplicate email detection

---

# Future Enhancements

- JWT Authentication
- Refresh Tokens
- Swagger/OpenAPI Documentation
- Docker Deployment
- Redis Caching
- Role-Based Authorization
- File Upload Service
- Email Service
- Unit Testing (JUnit + Mockito)
- Integration Testing
- GitHub Actions CI/CD
- Kubernetes Deployment

---

# Getting Started

## Clone the repository

```bash
git clone https://github.com/Rutwik2005/FleetOps.git
```

## Navigate into the project

```bash
cd FleetOps
```

## Configure PostgreSQL

Create a database named:

```
fleetops
```

Update your `application.properties` with your PostgreSQL credentials.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fleetops
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Run the application

```bash
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

# Development Roadmap

- ✅ Project Setup
- ✅ Database Configuration
- ✅ User Module
- 🔄 Authentication
- ⏳ Fleet Module
- ⏳ Shipment Module
- ⏳ Dispatch Module
- ⏳ Notifications
- ⏳ Docker
- ⏳ CI/CD
- ⏳ Production Deployment

---

# Author

**Rutwik Vaidya**

Bachelor of Technology – Computer Science & Engineering

Java Backend Developer | Spring Boot | PostgreSQL | REST APIs

GitHub: https://github.com/Rutwik2005

---

# License

This project is licensed under the MIT License.
