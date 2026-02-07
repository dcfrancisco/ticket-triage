# Learning Path by Weekend (macOS)

## Weekend 0 (Prep)
- Install tools: Homebrew; `brew install maven docker colima jq`. Install SDKMAN (`curl -s "https://get.sdkman.io" | bash`; `sdk install java 21-tem`). If Node is needed, install nvm (`brew install nvm`, init in `~/.zprofile`, `nvm install --lts`).
- Start Docker via Colima: `colima start --cpu 4 --memory 8 --disk 60`; verify with `docker ps`.
- Run Postgres: `docker run --name pg-ticket -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=ticket -p 5432:5432 -d postgres:16`. Install `psql` via `brew install libpq` and add to PATH.
- Create env file or exports for `DB_URL`, `DB_USER`, `DB_PASS`, `TRIAGE_AI_MODE`, `OPENAI_API_KEY` (if used).

## Weekend 1 (Core App)
- Scaffold Spring Boot 3 (Java 21) with Web, Validation, Data JPA, Flyway, OpenAPI, Testcontainers.
- Model domain: ticket aggregate; enums for status/category/priority; unit-test status transitions.
- Persistence: JPA entities/repos; Flyway V1 migration; Testcontainers-backed repo test (Docker/Colima running).
- API basics: controllers + DTO validation for create/get/list/update-status; global exception handler returning problem+json.
- Docs: OpenAPI starter; add `docs/examples.http` with sample requests.

## Weekend 2 (Triage & Polish)
- Triage: implement `TriageAiPort`; stub adapter default; OpenAI adapter behind `TRIAGE_AI_MODE`; persist `triage_results`; ensure safe fallback (OTHER, P3, 0.0).
- API enhancements: triage endpoint; pagination/filtering on list; tighten validation messages.
- Testing: controller + service tests; extend Testcontainers coverage.
- README: include architecture diagram and quickstart instructions.

## Stretch (Optional)
- Similarity: embeddings endpoint; pgvector toggle or text-similarity fallback.
- Security: basic auth/API key.
- Platform: k8s manifests/Helm (Deployment, Service, Secret, ConfigMap); practice `kubectl apply` with kind/minikube.
- CI: workflow running tests and lint.

## Tips
- Keep classes small; prefer constructor injection.
- Avoid logging secrets or full descriptions in production.
- Use pagination/filtering on list endpoints to avoid large responses.
- Write tests alongside features (service + controller + migration happy-path check).
