# Quick Start

This repository is documentation-first on `main`. Use this guide to get oriented quickly, choose the right branch, and prepare your machine for the staged implementation.

## 1. Choose how you want to use the repo

### Option A: Learn the architecture first

Stay on `main` and read:

1. [README.md](README.md)
2. [WEEK1_GUIDE.md](WEEK1_GUIDE.md)
3. [WEEK2_GUIDE.md](WEEK2_GUIDE.md)
4. [docs/architecture.md](docs/architecture.md)

### Option B: Run the implementation when available

Check out the appropriate staged branch:

- `solutions-week1` for the backend foundation
- `solutions-week2` for the AI triage layer
- `solutions-stretch` for optional advanced additions

See [SOLUTION_BRANCHES.md](SOLUTION_BRANCHES.md) for the branch strategy and naming.

## 2. Backend quick start

The backend implementation is designed around Spring Boot, PostgreSQL, Flyway, and Docker.

### Docker and database startup

If your chosen solution branch includes `compose.yml` or `docker-compose.yml`:

```bash
docker compose up -d
```

If the branch does not yet include compose support, start PostgreSQL directly:

```bash
docker run --name pg-ticket \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=ticket \
  -p 5432:5432 \
  -d postgres:16
```

### Spring Boot run

When the implementation branch includes the Maven wrapper or a Maven project:

```bash
./mvnw spring-boot:run
```

Or, if you use a system Maven installation:

```bash
mvn spring-boot:run
```

## 3. AI configuration

Recommended environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/ticket
export DB_USER=postgres
export DB_PASS=postgres
export TRIAGE_AI_MODE=stub
export OPENAI_API_KEY=
export OPENAI_MODEL=gpt-4.1-mini
export LLM_BASE_URL=http://localhost:11434
export LLM_MODEL=llama3
```

### OpenAI setup

Use `TRIAGE_AI_MODE=openai` and set `OPENAI_API_KEY`.

### Local model setup

Use `TRIAGE_AI_MODE=local` and point `LLM_BASE_URL` to your local provider such as Ollama or LM Studio.

## 4. Frontend quick start

The Angular application is planned for Week 3 and is not present on the `main` branch today.

When the frontend branch exists and includes an Angular workspace, the typical startup flow will be:

```bash
npm install
ng serve
```

or:

```bash
npm install
npm start
```

## 5. What to read next

- [INSTALLATION.md](INSTALLATION.md) for detailed setup
- [WEEK1_GUIDE.md](WEEK1_GUIDE.md) for backend architecture
- [WEEK2_GUIDE.md](WEEK2_GUIDE.md) for AI design
- [WEEK3_GUIDE.md](WEEK3_GUIDE.md) for the planned frontend product layer
- [TROUBLESHOOTING.md](TROUBLESHOOTING.md) if setup fails