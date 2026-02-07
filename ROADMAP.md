# Roadmap & Progress

## Milestones
- [x] Project learning guides and setup docs (weekend plans, macOS, env, project setup)
- [x] PDF export script with bundled outputs
- [x] Tagging scheme for phases and capabilities
- [x] Funding link (Buy Me a Coffee)
- [ ] Spring Boot project scaffold (Java 21, Web, Validation, JPA, Flyway, OpenAPI, Testcontainers)
- [ ] Flyway V1 migration and ticket repository tests
- [ ] Core API: create/get/list/status update with validation and problem+json errors
- [ ] AI triage flow: ports, stub + OpenAI/local adapters, idempotent persistence (V2 migration)
- [ ] Similarity endpoint with pgvector (feature-flagged) and fallback
- [ ] Docker compose for app + Postgres; optional k8s manifests (stretch)
- [ ] CI workflow (tests, optional image build/push)

## Suggested Sequence
1) Scaffold app and apply V1 migration
2) Repositories + Testcontainers tests
3) Core API endpoints + validation + error handler
4) Triage service + V2 migration + AI adapters (stub/OpenAI/local)
5) List filters + pagination + docs/examples.http
6) pgvector integration + similarity endpoint (optional)
7) Docker compose; k8s/Cloud deploy (stretch)
8) CI workflow and polish
