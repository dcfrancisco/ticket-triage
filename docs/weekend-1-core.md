# Weekend 1 — Core App

## Goals
- Scaffold the project with clean layers.
- Implement core ticket CRUD and status transitions.
- Set up persistence with Flyway and repository tests.

## Steps
1. Project scaffold
   - Create Spring Boot 3 (Java 21) project with: Web, Validation, Data JPA, Flyway, OpenAPI, Testcontainers.
   - Set group/package `com.example.tickettriage` (or chosen org).
2. Domain modeling
   - Define enums: Status (NEW, TRIAGED, IN_PROGRESS, DONE), Category, Priority.
   - Create domain model (ticket aggregate) with transition rules; add unit tests.
3. Persistence setup
   - Flyway V1 migration: create `tickets` table (uuid PK, subject, description, customer_email, status, created_at, updated_at).
   - JPA entity + repository for tickets.
   - Testcontainers integration test: repository save/find round-trip (Docker/Colima must be running before tests).
4. API layer (initial)
   - DTOs + validation: subject 5-120, description 20-4000, customerEmail email.
   - Controllers: POST /api/tickets (create), GET /api/tickets/{id}, GET /api/tickets (with filters stub), POST /api/tickets/{id}/status (basic transition enforcement).
   - Global exception handler returning problem+json.
5. Observability & docs
   - Enable OpenAPI/Swagger UI.
   - Add basic request logging filter (no secrets, no full descriptions in prod mode).
   - Create `docs/examples.http` with sample create/get/status requests.
6. Containerization (compose-first)
   - Write Dockerfile.
   - Create docker compose file to run app + Postgres (reuse local env vars); verify `docker compose up` works.
   - Defer Kubernetes to the stretch weekend; stay on compose for now.
7. Quick checks
   - `./mvnw test` (with Colima running for Testcontainers).
   - `./mvnw spring-boot:run` against local Postgres; hit endpoints from `examples.http`.

## Deliverables
- Running core API with ticket creation/read/status update.
- Flyway V1 applied; repository integration test passing.
- Dockerfile + compose for local run.
