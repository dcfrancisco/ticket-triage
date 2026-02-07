# macOS Setup Cheatsheet

## Prereqs
- Homebrew installed.
- Apple Silicon note: paths often start with `/opt/homebrew/...` instead of `/usr/local/...`.

## Install Tooling
```sh
# Core tools
brew install maven docker colima jq

# SDKMAN for Java
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21-tem   # or current LTS

# nvm for Node.js (optional if you need node tooling)
brew install nvm
mkdir -p ~/.nvm
echo 'export NVM_DIR="$HOME/.nvm"' >> ~/.zprofile
echo '[ -s "/opt/homebrew/opt/nvm/nvm.sh" ] && . "/opt/homebrew/opt/nvm/nvm.sh"' >> ~/.zprofile
source ~/.zprofile
nvm install --lts

# Optional: psql client
brew install libpq
echo 'export PATH="/opt/homebrew/opt/libpq/bin:$PATH"' >> ~/.zprofile

# Add Java to PATH (adjust if using /usr/local/opt)
```
Reload shell: `source ~/.zprofile`.
```
Reload shell: `source ~/.zprofile`.

## Containers on macOS
```sh
# Start Docker via Colima (adjust resources as needed)
colima start --cpu 4 --memory 8 --disk 60
# Verify
docker ps
```

## Postgres via Docker
```sh
docker run --name pg-ticket \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=ticket \
  -p 5432:5432 -d postgres:16
```
Connect: `psql -h localhost -U postgres -d ticket` (password: `postgres`).

## Project Env Vars (example)
```sh
export DB_URL=jdbc:postgresql://localhost:5432/ticket
export DB_USER=postgres
export DB_PASS=postgres
export TRIAGE_AI_MODE=stub   # or openai
export OPENAI_API_KEY=sk-... # only if using openai
```

## Running Tests with Testcontainers
- Ensure Colima/Docker is running before `./mvnw test`.

## Next Steps
- Initialize Spring Boot app (Java 21) with Web, Validation, Data JPA, Flyway, OpenAPI, Testcontainers.
- Add Flyway V1 migration and run against local Postgres.
- Keep secrets out of git; use env vars.
