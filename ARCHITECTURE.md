# Technical Architecture

Detailed explanation of every class and element in the project.

---

## Architectural Pattern

The project applies **Clean Architecture** + **Domain-Driven Design (DDD)**. Dependencies point inward: Infrastructure → Application → Domain. The Domain layer has zero framework dependencies.

```
┌─────────────────────────────────────────────┐
│           Infrastructure Layer              │
│  (Spring, JPA, REST, Config, Handlers)      │
│  ┌───────────────────────────────────────┐  │
│  │         Application Layer             │  │
│  │  (Use Cases, DTOs, Mappers)           │  │
│  │  ┌─────────────────────────────────┐  │  │
│  │  │        Domain Layer             │  │  │
│  │  │  (Models, Interfaces, Rules)    │  │  │
│  │  └─────────────────────────────────┘  │  │
│  └───────────────────────────────────────┘  │
└─────────────────────────────────────────────┘
```

---

## Domain Layer

### `Franchise` — `domain/model/Franchise.java`

The **aggregate root**. Plain Java class (no annotations) representing the core business entity.

| Field    | Type             | Description                  |
|----------|------------------|------------------------------|
| `id`     | `UUID`           | Unique identifier            |
| `name`   | `String`         | Franchise name               |
| `address`| `String`         | Physical address             |
| `phone`  | `String`         | Contact phone number         |
| `email`  | `String`         | Contact email                |
| `status` | `FranchiseStatus`| `ACTIVE` or `INACTIVE`       |

Key methods: `activate()` and `deactivate()` — the only way to change status, enforcing the business rule that status transitions are explicit operations.

---

### `FranchiseStatus` — `domain/model/FranchiseStatus.java`

Enum with values `ACTIVE` and `INACTIVE`. Kept in the domain to prevent status strings from leaking across layers.

---

### `FranchiseRepository` — `domain/repository/FranchiseRepository.java`

Interface (port) defining the persistence contract. The domain depends on this abstraction, never on Spring Data directly.

| Method              | Returns              |
|---------------------|----------------------|
| `save(Franchise)`   | `Franchise`          |
| `findById(UUID)`    | `Optional<Franchise>`|
| `findAll()`         | `List<Franchise>`    |
| `deleteById(UUID)`  | `void`               |

---

### `FranchiseValidationService` — `domain/service/FranchiseValidationService.java`

Domain service for business validation. Validates that:
- `name` is not null or blank → throws `IllegalArgumentException`
- `email` contains `@` → throws `IllegalArgumentException`

Kept in the domain because these rules belong to the business, not to input parsing.

---

### `FranchiseNotFoundException` — `domain/exception/FranchiseNotFoundException.java`

Custom `RuntimeException` thrown when a franchise lookup by ID returns no result. Carries the searched UUID in the message. Caught and mapped to HTTP 404 by `GlobalExceptionHandler`.

---

## Application Layer

### DTOs — `application/dto/`

Records (immutable value objects) used to transfer data across layer boundaries. They carry Jakarta Validation annotations so that the framework can validate input before it reaches use cases.

| Class                    | Purpose               | Validated fields              |
|--------------------------|-----------------------|-------------------------------|
| `FranchiseRequest`       | Create operation input| `name`, `address`, `email`    |
| `UpdateFranchiseRequest` | Update operation input| `name`, `address`, `email`    |
| `FranchiseResponse`      | API response output   | — (output only, no validation)|

---

### `FranchiseMapper` — `application/mapper/FranchiseMapper.java`

Converts between domain objects and DTOs. Keeps mapping logic in one place so use cases and controllers remain free of conversion code.

| Method                       | Converts                           |
|------------------------------|------------------------------------|
| `toDomain(FranchiseRequest)` | DTO → `Franchise` (generates UUID) |
| `toResponse(Franchise)`      | `Franchise` → `FranchiseResponse`  |

---

### Use Cases — `application/usecase/`

Each use case is a single class with one public method `execute(...)`. This enforces the **Single Responsibility Principle** and makes each business operation independently testable.

#### `CreateFranchiseUseCase`
Flow: map request → validate → save → map response.
Dependencies: `FranchiseRepository`, `FranchiseValidationService`, `FranchiseMapper`.

#### `GetFranchisesUseCase`
Flow: fetch all from repository → stream-map to response DTOs.
Dependencies: `FranchiseRepository`, `FranchiseMapper`.

#### `GetFranchiseByIdUseCase`
Flow: find by id → if absent throw `FranchiseNotFoundException` → map to response.
Dependencies: `FranchiseRepository`, `FranchiseMapper`.

#### `UpdateFranchiseUseCase`
Flow: find existing → build updated `Franchise` preserving `id` and `status` → validate → save → map response.
Dependencies: `FranchiseRepository`, `FranchiseValidationService`, `FranchiseMapper`.

#### `DeleteFranchiseUseCase`
Flow: find by id (throws if absent) → delete.
Dependencies: `FranchiseRepository`.

---

## Infrastructure Layer

### `BeanConfiguration` — `infrastructure/config/BeanConfiguration.java`

`@Configuration` class that wires all application-layer objects as Spring beans. Explicit bean definitions instead of component scanning keep the infrastructure boundaries clear and make dependency graphs visible.

---

### `OpenApiConfig` — `infrastructure/config/OpenApiConfig.java`

Produces the `OpenAPI` bean used by SpringDoc to generate the Swagger UI. Configured with title, version, contact, and server URL. Adding new API groups or security schemes requires only changes here.

---

### `FranchiseEntity` — `infrastructure/drivenadapters/jpa/FranchiseEntity.java`

JPA `@Entity` mapped to the `franchises` table. Separate from the domain `Franchise` to avoid coupling persistence annotations to business logic.

| Column   | Constraint       |
|----------|------------------|
| `id`     | UUID, primary key|
| `name`   | NOT NULL         |
| `email`  | NOT NULL         |
| `status` | ENUM (string)    |

---

### `JpaFranchiseRepositorySpring` — `infrastructure/drivenadapters/jpa/JpaFranchiseRepositorySpring.java`

Extends `JpaRepository<FranchiseEntity, UUID>`. Spring Data generates the implementation at runtime. This interface is internal to the infrastructure layer.

---

### `JpaFranchiseRepository` — `infrastructure/drivenadapters/jpa/JpaFranchiseRepository.java`

**Adapter** that implements the domain's `FranchiseRepository` interface by delegating to `JpaFranchiseRepositorySpring`. Contains `toEntity()` and `toDomain()` private methods for converting between persistence and domain models.

This adapter is the only class that bridges JPA and the domain — if the persistence technology changes, only this class changes.

---

### `FranchiseController` — `infrastructure/entrypoints/FranchiseController.java`

`@RestController` at `/api/franchises`. Delegates all business logic to use cases. Annotated with SpringDoc's `@Tag`, `@Operation`, and `@ApiResponses` to generate complete Swagger documentation.

| Endpoint          | Use Case                  | Response   |
|-------------------|---------------------------|------------|
| `POST /`          | `CreateFranchiseUseCase`  | 201 Created|
| `GET /`           | `GetFranchisesUseCase`    | 200 OK     |
| `GET /{id}`       | `GetFranchiseByIdUseCase` | 200 OK     |
| `PUT /{id}`       | `UpdateFranchiseUseCase`  | 200 OK     |
| `DELETE /{id}`    | `DeleteFranchiseUseCase`  | 204 No Content|

---

### `GlobalExceptionHandler` — `infrastructure/entrypoints/handler/GlobalExceptionHandler.java`

`@RestControllerAdvice` that intercepts exceptions and returns consistent JSON error responses. Keeps try/catch blocks out of controllers.

| Exception                        | HTTP Status | Response type            |
|----------------------------------|-------------|--------------------------|
| `FranchiseNotFoundException`     | 404         | `ErrorResponse`          |
| `IllegalArgumentException`       | 400         | `ErrorResponse`          |
| `MethodArgumentNotValidException`| 400         | `ValidationErrorResponse`|
| `Exception` (fallback)           | 500         | `ErrorResponse`          |

Inner records `ErrorResponse` and `ValidationErrorResponse` define the JSON shape.

---

## Configuration Files

### `application.properties`

Base configuration loaded in all profiles. Sets `spring.profiles.active` from `APP_PROFILE` env var (defaults to `local`). Contains SpringDoc paths.

### `application-local.properties`

H2 in-memory database. H2 console enabled. `ddl-auto=create-drop`. Debug logging. Used for local development without any external dependencies.

### `application-dev.properties`

H2 file-based database by default; overridable via `DB_URL` env var for external DBs. Comments show connection strings for AWS RDS, Azure SQL, and GCP Cloud SQL.

### `application-qa.properties`

Requires all `DB_*` environment variables. `ddl-auto=validate`. Connection pool configured. Cloud provider variables (`cloud.provider`, `cloud.aws.*`, `cloud.azure.*`, `cloud.gcp.*`) for secrets management integration.

### `application-pdn.properties`

Strictest configuration: Swagger disabled, minimal logging, `ddl-auto=validate`, larger connection pool defaults, all credentials via secrets manager.

---

## CI/CD Workflows

### `ci.yml`

Triggers on push/PR to `main` and `develop`. Steps: checkout → JDK 17 → `mvn clean test` → upload test results → `mvn package` → upload JAR → Docker build validation.

### `cd.yml`

Triggers on push to `main` or manual dispatch. Accepts `environment` (`qa`/`pdn`) and `cloud_provider` (`aws`/`azure`/`gcp`) inputs.

Three parallel deploy jobs run conditionally:
- `deploy-aws`: ECR push + ECS force deployment
- `deploy-azure`: ACR push + App Service deployment
- `deploy-gcp`: Artifact Registry push + Cloud Run deployment

---

## Test Classes

### Unit Tests — `src/test/java/.../`

| Class                          | Tests | What is covered                          |
|--------------------------------|-------|------------------------------------------|
| `FranchiseValidationServiceTest` | 5   | Valid/invalid name and email cases       |
| `FranchiseMapperTest`          | 3     | Field mapping, UUID generation           |
| `CreateFranchiseUseCaseTest`   | 2     | Happy path, validation failure           |
| `GetFranchisesUseCaseTest`     | 2     | Non-empty and empty repository           |
| `GetFranchiseByIdUseCaseTest`  | 2     | Found and not-found cases                |
| `UpdateFranchiseUseCaseTest`   | 2     | Successful update, not-found exception   |
| `DeleteFranchiseUseCaseTest`   | 2     | Successful delete, not-found exception   |

### Integration Tests — `FranchiseControllerIntegrationTest`

Uses `@SpringBootTest` + `@AutoConfigureMockMvc` with real H2 database. Covers all CRUD endpoints including validation errors (400) and not-found responses (404). `@BeforeEach` deletes all rows to ensure test isolation.
