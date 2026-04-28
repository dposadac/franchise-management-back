# Franchise Management Backend

REST API for managing franchises built with **Spring Boot 3**, **Clean Architecture**, and **Domain-Driven Design (DDD)**. Supports multi-cloud deployment (AWS, Azure, GCP) and multiple environments (local, dev, qa, pdn).

---

## Table of Contents

- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Running Locally](#running-locally)
- [Environment Variables](#environment-variables)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Docker](#docker)
- [CI/CD](#cicd)
- [Cloud Deployment](#cloud-deployment)

---

## Architecture

The project follows **Clean Architecture** with three distinct layers:

```
src/main/java/com/accenture/franchise/
├── domain/              # Business rules — no framework dependencies
│   ├── model/           # Franchise, FranchiseStatus
│   ├── repository/      # FranchiseRepository (interface)
│   ├── service/         # FranchiseValidationService
│   └── exception/       # FranchiseNotFoundException
├── application/         # Use cases — orchestrates domain objects
│   ├── usecase/         # Create, Get, GetById, Update, Delete
│   ├── dto/             # FranchiseRequest, FranchiseResponse, UpdateFranchiseRequest
│   └── mapper/          # FranchiseMapper
└── infrastructure/      # Framework adapters
    ├── config/          # BeanConfiguration, OpenApiConfig
    ├── drivenadapters/  # JPA persistence adapters
    │   └── jpa/         # FranchiseEntity, JpaFranchiseRepository
    └── entrypoints/     # REST controllers
        ├── FranchiseController
        └── handler/     # GlobalExceptionHandler
```

## Classes Diagram model
![alt text](Analisis_Sistema_Ventas_Franquicia-03_DiagramaClases.jpg)


## Prerequisites

| Tool    | Version  |
|---------|----------|
| Java    | 17+      |
| Maven   | 3.9+     |
| Docker  | 24+      |

---

## Running Locally

### 1. Clone the repository

```bash
git clone <repository-url>
cd franchise-management-back
```

### 2. Run with Maven (H2 in-memory, no external DB required)

```bash
mvnw.cmd spring-boot:run          # Windows
./mvnw spring-boot:run            # Linux / macOS
```

The application starts at `http://localhost:8080`.

### 3. Swagger UI

```
http://localhost:8080/swagger-ui.html
```

### 4. H2 Console (local/dev only)

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:franchisedb
Username: sa
Password: (empty)
```

---

## Environment Variables

Copy `.env.example` to `.env` and fill in values:

```bash
cp .env.example .env
```

| Variable           | Description                              | Default    |
|--------------------|------------------------------------------|------------|
| `APP_PROFILE`      | Active Spring profile                    | `local`    |
| `PORT`             | Server port                              | `8080`     |
| `DB_URL`           | JDBC connection string                   | H2 in-mem  |
| `DB_DRIVER`        | JDBC driver class                        | H2 driver  |
| `DB_USERNAME`      | Database username                        | `sa`       |
| `DB_PASSWORD`      | Database password                        | (empty)    |
| `DB_PLATFORM`      | Hibernate dialect                        | H2 dialect |
| `CLOUD_PROVIDER`   | Cloud target: `aws` / `azure` / `gcp`   | `aws`      |
| `AWS_REGION`       | AWS region                               | `us-east-1`|
| `GCP_PROJECT_ID`   | GCP project ID                           | (empty)    |

See `.env.example` for the full list.

---

## API Endpoints

Base URL: `http://localhost:8080/api/franchises`

| Method   | Path    | Description           | Status |
|----------|---------|-----------------------|--------|
| `POST`   | `/`     | Create a franchise    | 201    |
| `GET`    | `/`     | Get all franchises    | 200    |
| `GET`    | `/{id}` | Get franchise by UUID | 200    |
| `PUT`    | `/{id}` | Update a franchise    | 200    |
| `DELETE` | `/{id}` | Delete a franchise    | 204    |

### Request body (POST / PUT)

```json
{
  "name": "Franchise Name",
  "address": "123 Main Street",
  "phone": "555-1234",
  "email": "contact@franchise.com"
}
```

### Response body

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Franchise Name",
  "address": "123 Main Street",
  "phone": "555-1234",
  "email": "contact@franchise.com",
  "status": "ACTIVE"
}
```

### Error response

```json
{
  "status": 404,
  "message": "Franchise not found with id: 550e...",
  "timestamp": "2026-04-23T10:00:00"
}
```

---

## Testing

### Run all tests

```bash
mvnw.cmd clean test          # Windows
./mvnw clean test            # Linux / macOS
```

### Test coverage

| Layer                        | Type        | Tests |
|------------------------------|-------------|-------|
| `FranchiseValidationService` | Unit        | 5     |
| `FranchiseMapper`            | Unit        | 3     |
| `CreateFranchiseUseCase`     | Unit        | 2     |
| `GetFranchisesUseCase`       | Unit        | 2     |
| `GetFranchiseByIdUseCase`    | Unit        | 2     |
| `UpdateFranchiseUseCase`     | Unit        | 2     |
| `DeleteFranchiseUseCase`     | Unit        | 2     |
| `FranchiseController`        | Integration | 9     |
| **Total**                    |             | **27**|

---

## Docker

### Build image

```bash
docker build -t franchise-management-back .
```

### Run with local profile (H2)

```bash
docker run -p 8080:8080 -e APP_PROFILE=local franchise-management-back
```

### Run with external database

```bash
docker run -p 8080:8080 --env-file .env franchise-management-back
```

---

## CI/CD

Pipelines defined in `.github/workflows/`:

| File     | Trigger                          | Description               |
|----------|----------------------------------|---------------------------|
| `ci.yml` | Push / PR → `main`, `develop`   | Build, test, Docker build |
| `cd.yml` | Push → `main` / manual dispatch | Deploy to AWS/Azure/GCP   |

### Required GitHub Secrets

| Secret                   | Used by   |
|--------------------------|-----------|
| `AWS_ACCESS_KEY_ID`      | AWS       |
| `AWS_SECRET_ACCESS_KEY`  | AWS       |
| `AZURE_CREDENTIALS`      | Azure     |
| `ACR_USERNAME`           | Azure     |
| `ACR_PASSWORD`           | Azure     |
| `GCP_CREDENTIALS`        | GCP       |
| `DB_URL`                 | All envs  |
| `DB_USERNAME`            | All envs  |
| `DB_PASSWORD`            | All envs  |
| `DB_DRIVER`              | All envs  |
| `DB_PLATFORM`            | All envs  |

### Required GitHub Variables

| Variable          | Example                   |
|-------------------|---------------------------|
| `CLOUD_PROVIDER`  | `aws`                     |
| `AWS_REGION`      | `us-east-1`               |
| `ECS_CLUSTER`     | `franchise-cluster`       |
| `ECS_SERVICE`     | `franchise-service`       |
| `GCP_PROJECT_ID`  | `my-gcp-project`          |
| `GCP_REGION`      | `us-central1`             |
| `ACR_LOGIN_SERVER`| `myregistry.azurecr.io`   |
| `AZURE_APP_NAME`  | `franchise-app`           |

---

## Cloud Deployment

Switch cloud providers by setting `CLOUD_PROVIDER` and the corresponding DB connection string.

### AWS (RDS PostgreSQL + ECS)

```env
CLOUD_PROVIDER=aws
APP_PROFILE=pdn
DB_URL=jdbc:postgresql://<rds-host>:5432/franchisedb
DB_DRIVER=org.postgresql.Driver
DB_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
```

### Azure (SQL Database + App Service)

```env
CLOUD_PROVIDER=azure
APP_PROFILE=pdn
DB_URL=jdbc:sqlserver://<server>.database.windows.net:1433;database=franchisedb;encrypt=true
DB_DRIVER=com.microsoft.sqlserver.jdbc.SQLServerDriver
DB_PLATFORM=org.hibernate.dialect.SQLServerDialect
```

### GCP (Cloud SQL + Cloud Run)

```env
CLOUD_PROVIDER=gcp
APP_PROFILE=pdn
DB_URL=jdbc:postgresql:///franchisedb?cloudSqlInstance=<project>:<region>:<instance>&socketFactory=com.google.cloud.sql.postgres.SocketFactory
DB_DRIVER=org.postgresql.Driver
DB_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
```
