# Copilot Instructions — AI Support Ticket Triage (Spring Boot)

You are assisting in building a small but production-flavored Spring Boot service.

## Goals

- Build an "AI Support Ticket Triage" backend.
- Emphasize clean architecture: API -> Application -> Domain -> Infrastructure.
- Keep scope suitable for 2 weekends, but with real structure: validation, tests, docs, migrations, error handling.

## Tech Stack

- Java 21
- Spring Boot 3.x (Web, Validation, Data JPA)
- PostgreSQL + pgvector (preferred for similarity) with a fallback non-vector path behind a feature flag.
- Flyway (DB migrations)
- AI client via Spring AI or a thin HTTP wrapper; support stub, OpenAI, and local HTTP LLMs (e.g., Ollama/vLLM) behind config.
- JUnit 5 + Spring Boot Test + Testcontainers (Postgres)

## Dev Environment

- Prefer SDKMAN for managing Java 21; nvm for Node tooling if needed.
- Use docker compose first for local app + Postgres; k8s is a stretch goal.

## Architecture Rules

- Domain is pure Java, no Spring annotations in domain.
- Controllers only map HTTP <-> DTOs. No business logic in controllers.
- Application services orchestrate: create ticket, triage ticket, search tickets.
- Infrastructure contains:
  - JPA entities + repositories
  - External AI client implementation (stub, OpenAI, local HTTP LLM)
  - Vector/embedding storage adapter (pgvector preferred; fallback text fingerprint)
- Use interfaces/ports in application layer for AI and embeddings storage.

## Functional Requirements

- Ticket lifecycle: NEW -> TRIAGED -> IN_PROGRESS -> DONE
- Core endpoints:
  - POST /api/tickets (create)
  - GET /api/tickets/{id}
  - GET /api/tickets (filter by status, category, priority; pagination)
  - POST /api/tickets/{id}/triage (AI-assisted)
  - POST /api/tickets/{id}/status (change status)
  - GET /api/tickets/{id}/similar (pgvector-backed if enabled; fallback allowed)
- Triage output is deterministic JSON:
  - summary (string, <= 500 chars)
  - category (enum: BILLING, TECHNICAL, ACCOUNT, FEATURE_REQUEST, OTHER)
  - priority (enum: P0..P3)
  - confidence (0..1)
  - suggestedResponse (string, optional)
- If AI fails, return a safe fallback (category OTHER, priority P3, confidence 0.0)

## Non-Functional Requirements

- Input validation with clear error messages (problem+json or consistent error format)
- Idempotency: triage endpoint should not create duplicates; store triage result per ticket
- Observability: structured logs; do not log secrets or full ticket text in production mode
- Config via application.yml; all secrets via env vars

## Data Model (minimum)

- tickets
  - id (uuid)
  - subject
  - description
  - customerEmail
  - status
  - createdAt, updatedAt
- triage_results
  - ticket_id (uuid, unique)
  - summary
  - category
  - priority
  - confidence
  - model
  - createdAt
- optional embeddings:
  - ticket_id (uuid)
  - embedding vector OR a text fingerprint fallback

## Testing

- Unit tests for domain/application services
- Integration tests for repositories and controllers using Testcontainers Postgres
- Provide sample requests in `/docs/examples.http` or curl snippets in README

## Deliverables

- Working app: `./mvnw spring-boot:run`
- Flyway migrations (including pgvector enablement when used)
- OpenAPI/Swagger enabled
- README with architecture diagram (ASCII or Mermaid)
- Env vars documented (see docs/environment.md); no secrets committed.

When generating code:

- Prefer small, readable classes.
- Include TODO comments for stretch goals, but do not bloat scope.
- Always add minimal tests when adding features.
