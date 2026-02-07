# Environment Variables

Recommended variables for local development and deployment. Do not commit secrets.

## Core Application
- `DB_URL` — JDBC URL (e.g., `jdbc:postgresql://localhost:5432/ticket`).
- `DB_USER` — database user (e.g., `postgres`).
- `DB_PASS` — database password.
- `SPRING_PROFILES_ACTIVE` — e.g., `local` or `dev`.

## AI/Triage
- `TRIAGE_AI_MODE` — `stub` (default) or `openai`.
- `OPENAI_API_KEY` — required if using `openai` mode.
- `OPENAI_MODEL` — optional; default from client config if unset.

## Optional Observability
- `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` — actuator exposure if enabled.

## Optional Similarity/Embeddings
- `EMBEDDINGS_ENABLED` — `true|false` to toggle embedding features.
- `VECTOR_DIMENSION` — if using pgvector.

## Local .env Example (do not commit)
```
DB_URL=jdbc:postgresql://localhost:5432/ticket
DB_USER=postgres
DB_PASS=postgres
SPRING_PROFILES_ACTIVE=local
TRIAGE_AI_MODE=stub
OPENAI_API_KEY=
OPENAI_MODEL=gpt-4.1-mini
EMBEDDINGS_ENABLED=false
```

## Running with env vars
- CLI: `DB_URL=... DB_USER=... DB_PASS=... ./mvnw spring-boot:run`
- docker compose: reference `${DB_URL}` etc. via an `.env` file (gitignored).
- VS Code/IntelliJ: set Run Configuration environment variables (do not store secrets in VCS).
