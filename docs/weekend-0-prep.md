# [week0] Weekend 0 — Prep (macOS)

## Goals
- Have tooling, container runtime, and database ready.
- Verify the ability to build and run a minimal Spring Boot app against Postgres.

## Steps
1. Install tools
   - Homebrew
   - `brew install maven docker colima jq`
   - SDKMAN: `curl -s "https://get.sdkman.io" | bash`; `source "$HOME/.sdkman/bin/sdkman-init.sh"`; `sdk install java 21-tem`
   - Optional Node: `brew install nvm`; init in `~/.zprofile`; `nvm install --lts`
   - Optional psql: `brew install libpq`; add PATH: `echo 'export PATH="/opt/homebrew/opt/libpq/bin:$PATH"' >> ~/.zprofile`
2. Start Docker
   - `colima start --cpu 4 --memory 8 --disk 60`
   - Verify: `docker ps`
3. Run Postgres locally
   - `docker run --name pg-ticket -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=ticket -p 5432:5432 -d postgres:16`
   - Connect: `psql -h localhost -U postgres -d ticket` (password `postgres`)
4. Env vars (example)
   - `DB_URL=jdbc:postgresql://localhost:5432/ticket`
   - `DB_USER=postgres`, `DB_PASS=postgres`
   - `TRIAGE_AI_MODE=stub`, `OPENAI_API_KEY=...` (if used)
5. Verify Java + Maven
   - `java -version` (should show 21)
   - `mvn -v`
6. Quick smoke test
   - Generate bare Spring Boot app (Web only) and run `./mvnw test` with Colima running.

## Deliverables
- Running Postgres container.
- Verified Java 21/Maven/tooling.
- Env vars recorded (not committed).
