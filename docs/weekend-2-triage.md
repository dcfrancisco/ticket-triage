# [week2] Weekend 2 — Triage & Polish

## Goals
- Add AI triage flow and persist triage results.
- Improve API with pagination/filtering and better validation.
- Strengthen tests and docs.

## Steps
1. Triage domain/application
   - Define `TriageAiPort` returning deterministic result (summary <=500, category enum, priority enum, confidence 0..1, optional suggestedResponse).
   - Add application service to invoke port, persist result, and guard idempotency (one triage per ticket).
2. Persistence
   - Flyway V2: `triage_results` table (ticket_id FK unique, summary, category, priority, confidence, model, created_at).
   - JPA entity + repository for triage results.
3. AI adapters
   - Stub adapter (default).
   - OpenAI adapter gated by `TRIAGE_AI_MODE=openai` and `OPENAI_API_KEY`; include safe fallback to OTHER/P3/0.0.
4. API layer
   - POST /api/tickets/{id}/triage to run AI and store result; return stored payload.
   - GET /api/tickets with filters (status, category, priority) + pagination params.
   - Improve validation messages; ensure problem+json responses consistent.
5. Testing
   - Service-level tests for triage flow (idempotency, fallback).
   - Controller tests for triage endpoint and list filtering.
   - Extend Testcontainers coverage to triage_results persistence.
6. Docs & readiness
   - Update README with triage flow, env vars, and architecture diagram.
   - Expand `docs/examples.http` with triage and list-with-filters calls.
   - Optional: add basic auth/API key guard if desired.

## Deliverables
- Triage endpoint working with stub and optional OpenAI.
- Flyway V2 applied; triage persistence tested.
- Polished API responses and docs.
