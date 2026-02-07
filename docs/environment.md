# Environment Variables

Recommended variables for local development and deployment. Do not commit secrets.

## Core Application
- `DB_URL` — JDBC URL (e.g., `jdbc:postgresql://localhost:5432/ticket`).
- `DB_USER` — database user (e.g., `postgres`).
- `DB_PASS` — database password.
- `SPRING_PROFILES_ACTIVE` — e.g., `local` or `dev`.

## AI/Triage
- `TRIAGE_AI_MODE` — `stub` (default), `openai`, or `local`.
- `OPENAI_API_KEY` — required if using `openai` mode.
- `OPENAI_MODEL` — optional; default from client config if unset.
- `LLM_BASE_URL` — for local HTTP LLMs (e.g., `http://localhost:11434` for Ollama, or your own service).
- `LLM_API_KEY` — if the local or custom LLM requires it; otherwise leave empty.
- `LLM_MODEL` — model name when using `local` mode (e.g., `llama3`, `mistral`).

## Optional Observability
- `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` — actuator exposure if enabled.

## Optional Similarity/Embeddings
- `EMBEDDINGS_ENABLED` — `true|false` to toggle embedding features.
- `VECTOR_DIMENSION` — if using pgvector.
- `PGVECTOR_ENABLED` — `true|false` to control pgvector migration/usage (default true if extension available).

## Local .env Example (do not commit)
```
DB_URL=jdbc:postgresql://localhost:5432/ticket
DB_USER=postgres
DB_PASS=postgres
SPRING_PROFILES_ACTIVE=local
TRIAGE_AI_MODE=stub
OPENAI_API_KEY=
OPENAI_MODEL=gpt-4.1-mini
LLM_BASE_URL=http://localhost:11434
LLM_MODEL=llama3
LLM_API_KEY=
EMBEDDINGS_ENABLED=true
PGVECTOR_ENABLED=true
VECTOR_DIMENSION=1536
```

## Running with env vars
- CLI: `DB_URL=... DB_USER=... DB_PASS=... ./mvnw spring-boot:run`
- docker compose: reference `${DB_URL}` etc. via an `.env` file (gitignored).
- VS Code/IntelliJ: set Run Configuration environment variables (do not store secrets in VCS).
