# AI Support Ticket Triage — Learning Track

A structured, weekend-friendly roadmap to build a Spring Boot (Java 21) backend for AI-assisted ticket triage. This repo currently contains learning guides, macOS setup notes, and scripts to export docs to PDF.

## What’s inside
- `docs/architecture.md` — layered design and mermaid diagram.
- `docs/learning-path.md` — high-level plan by weekend.
- `docs/weekend-0-prep.md` — tooling and environment prep (macOS).
- `docs/weekend-1-core.md` — core app scaffold, CRUD, Flyway, tests.
- `docs/weekend-2-triage.md` — AI triage flow, persistence, API polish.
- `docs/stretch-weekend.md` — optional similarity, compose-to-k8s, cloud deployment options, CI.
- `docs/macos-setup.md` — SDKMAN/nvm/Docker/Colima setup.
- `docs/environment.md` — required environment variables and sample .env.
- `docs/project-setup.md` — generating the Spring Boot project via CLI, VS Code, or IntelliJ.
- `docs/index.md` — doc entry point.
- `scripts/export-docs.sh` — render PDFs via pandoc.

## Quick start (macOS)
1) Install tools
   - Homebrew
   - `brew install maven docker colima jq`
   - SDKMAN: `curl -s "https://get.sdkman.io" | bash`; `source "$HOME/.sdkman/bin/sdkman-init.sh"`; `sdk install java 21-tem`
   - Optional Node: `brew install nvm`; set up in `~/.zprofile`; `nvm install --lts`
   - Optional psql: `brew install libpq`; add PATH: `echo 'export PATH="/opt/homebrew/opt/libpq/bin:$PATH"' >> ~/.zprofile`
2) Start Docker runtime
   - `colima start --cpu 4 --memory 8 --disk 60`
3) Run Postgres locally
   - `docker run --name pg-ticket -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=ticket -p 5432:5432 -d postgres:16`
4) Environment variables (example)
   - `DB_URL=jdbc:postgresql://localhost:5432/ticket`
   - `DB_USER=postgres`, `DB_PASS=postgres`
   - `TRIAGE_AI_MODE=stub`, `OPENAI_API_KEY=...` (if using OpenAI adapter)
5) Export docs to PDF (optional)
   - Ensure pandoc is installed (`brew install pandoc`).
   - `chmod +x scripts/export-docs.sh && scripts/export-docs.sh`
   - PDFs land in `build/pdfs`.

## How to use the learning plan
- Pick the weekend file that matches the current phase and complete the steps and deliverables before moving on.
- Use docker compose first for local runs; k8s and cloud deployment are optional stretch goals.
- Keep the domain layer free of Spring, keep controllers thin, and add tests alongside features.
- When blocked on tooling or infra for more than ~30–60 minutes, capture notes and defer or ask for help.

## Next steps
- Scaffold the Spring Boot project per `docs/weekend-1-core.md`.
- Add initial Flyway migration, repository tests, and minimal API surface.

