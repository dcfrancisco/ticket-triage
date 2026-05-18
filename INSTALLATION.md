# Installation Guide

This guide explains how to prepare your machine for the AI Helpdesk Mentor project. The repository is currently documentation-first on `main`, so some runtime steps apply once you switch to an implementation branch.

## Installation goals

You want a setup that can support:

- Java 21 and Maven for the backend
- PostgreSQL for persistence
- Docker for local infrastructure and future compose-based startup
- Node.js and Angular CLI for the future frontend
- environment variables for AI provider configuration

## Prerequisites

### Java

- Required version: Java 21
- Recommended manager: SDKMAN

Install with SDKMAN:

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21-tem
java -version
```

### Maven

Install Maven if the implementation branch does not provide `./mvnw`:

```bash
brew install maven
mvn -version
```

### PostgreSQL

You can run PostgreSQL in Docker or install it locally.

Local install on macOS:

```bash
brew install postgresql@16
```

Docker-based option:

```bash
docker run --name pg-ticket \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=ticket \
  -p 5432:5432 \
  -d postgres:16
```

### Docker

Install Docker Desktop or use a local runtime such as Colima.

Colima example on macOS:

```bash
brew install docker colima
colima start --cpu 4 --memory 8 --disk 60
docker ps
```

### Node.js

Node.js is needed for the future Angular frontend.

Recommended manager:

```bash
brew install nvm
mkdir -p ~/.nvm
```

Then add nvm initialization to your shell profile and install the latest LTS release:

```bash
nvm install --lts
node -v
npm -v
```

### Angular CLI

Install Angular CLI when the frontend workspace becomes available:

```bash
npm install -g @angular/cli
ng version
```

## Environment configuration

Use environment variables rather than committing secrets.

### Core backend variables

```bash
export DB_URL=jdbc:postgresql://localhost:5432/ticket
export DB_USER=postgres
export DB_PASS=postgres
export SPRING_PROFILES_ACTIVE=local
```

### AI variables

```bash
export TRIAGE_AI_MODE=stub
export OPENAI_API_KEY=
export OPENAI_MODEL=gpt-4.1-mini
export LLM_BASE_URL=http://localhost:11434
export LLM_MODEL=llama3
export LLM_API_KEY=
```

### Optional retrieval variables

```bash
export EMBEDDINGS_ENABLED=true
export PGVECTOR_ENABLED=true
export VECTOR_DIMENSION=1536
```

## Backend startup flow

Once you are on a runnable implementation branch:

1. Start PostgreSQL.
2. Export the required environment variables.
3. Start the application with `./mvnw spring-boot:run` or `mvn spring-boot:run`.
4. Open Swagger UI once the app is running.

If the branch includes docker compose, prefer that path for local setup.

## Frontend startup flow

The Angular frontend is planned for Week 3 and is not present on `main` today.

When it is available:

1. Install Node.js and Angular CLI.
2. Run `npm install`.
3. Run `ng serve` or `npm start`.
4. Configure the frontend to point to the backend base URL.

## Troubleshooting setup issues

### Java version is wrong

- Run `java -version`.
- Make sure your shell is using Java 21.
- If you use SDKMAN, run `sdk use java 21-tem`.

### Maven cannot resolve dependencies

- Check internet connectivity.
- Check corporate proxy settings if applicable.
- Run `mvn -version` to confirm the expected Java runtime is active.

### Docker daemon is not available

- Start Docker Desktop or Colima.
- Verify with `docker ps`.

### PostgreSQL connection fails

- Confirm the container is running.
- Confirm the port is `5432`.
- Confirm `DB_URL`, `DB_USER`, and `DB_PASS` match the database setup.

### OpenAI calls fail

- Confirm `TRIAGE_AI_MODE=openai`.
- Confirm `OPENAI_API_KEY` is set in the same shell session.
- Check provider-specific rate limits and billing.

### Local model calls fail

- Confirm the local model server is actually running.
- Confirm `LLM_BASE_URL` points to the correct host and port.
- Confirm the model name exists in the local runtime.

For more operational issues, see [TROUBLESHOOTING.md](TROUBLESHOOTING.md).