# Master Scaffold Prompt (Copilot Chat)

Use this prompt in Copilot Chat to generate the full project. Group ID set to `ph.francisco`.

```text
Scaffold a complete Spring Boot 3.x (Java 21) Maven project named "ticket-triage" using package com.example.tickettriage and groupId ph.francisco.

Implement clean architecture with packages:
- api (controllers/dtos)
- application (services/ports)
- domain (models/rules)
- infrastructure (jpa, ai clients, config)

Dependencies:
- Spring Web, Validation, Spring Data JPA
- Flyway
- PostgreSQL driver
- springdoc-openapi (Swagger UI)
- Spring Boot Test, JUnit 5
- Testcontainers (Postgres)

Domain:
- Enums: TicketStatus {NEW, TRIAGED, IN_PROGRESS, DONE}, Category {BILLING, TECHNICAL, ACCOUNT, FEATURE_REQUEST, OTHER}, Priority {P0, P1, P2, P3}
- Ticket aggregate holds id, subject, description, customerEmail, status, timestamps
- Domain method enforces status transitions (NEW->TRIAGED->IN_PROGRESS->DONE). Support rollback/retriage only when allowRollback/force flag is true.
- Unit tests for transition matrix.

Persistence:
- Flyway migration V1__init.sql creates tickets table:
  id uuid PK, subject, description, customer_email, status, created_at, updated_at
- Flyway migration V2__triage_results.sql creates triage_results table:
  ticket_id uuid PK/FK unique, summary, category, priority, confidence, provider, model, created_at, updated_at
- JPA entities and repositories. Keep mapping separate from domain objects (use mappers).

Application:
- Ports:
  - TicketRepositoryPort
  - TriageResultRepositoryPort
  - TriageAiPort (triage(subject, description) -> TriageResult)
- Services:
  - TicketService: createTicket, getTicket, listTickets(filters, pageable), updateStatus
  - TriageService: triageTicket(id, force=false) with idempotency, and safe fallback if AI fails

Infrastructure:
- JPA adapters implementing repository ports
- AI adapters:
  - Stub adapter default: deterministic triage based on simple keyword rules
  - OpenAI adapter gated by TRIAGE_AI_MODE=openai and OPENAI_API_KEY
  - Local LLM adapter gated by TRIAGE_AI_MODE=local and LLM_BASE_URL
- Ensure AI output is parsed as strict JSON into:
  summary (<=500), category enum, priority enum, confidence 0..1, suggestedResponse optional
- If parsing fails, use safe fallback: OTHER/P3/0.0

API:
Base path /api
Endpoints:
1) POST /api/tickets (validate subject 5-120, description 20-4000, email)
2) GET /api/tickets/{id} returns ticket + triage if exists
3) GET /api/tickets filters by status/category/priority + pagination
4) POST /api/tickets/{id}/status body {status, allowRollback}
5) POST /api/tickets/{id}/triage supports ?force=true to overwrite; otherwise idempotent

Error handling:
- Global exception handler returns consistent JSON:
  type, title, status, detail, instance, errors[]
- 400 validation, 404 not found, 409 illegal transition.

Ops:
- application.yml reads DB_URL/DB_USER/DB_PASS env vars
- Provide docker-compose.yml for Postgres + app (app can be built locally)

Tests:
- Integration tests using Testcontainers Postgres:
  - repository roundtrip
  - MockMvc tests for create/get/status/triage
- Keep tests minimal but meaningful.

Docs:
- README with run instructions, env vars, Swagger URL, and Mermaid architecture diagram.
- Create /docs/examples.http with sample requests.

Generate all files: pom.xml, main app class, packages, migrations, compose, README, examples.http, and tests.
Keep scope tight; do not add unnecessary features.
```
