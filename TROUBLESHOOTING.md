# Troubleshooting

This guide covers common issues you may hit while setting up or extending AI Helpdesk Mentor.

## Database startup issues

### PostgreSQL container does not start

Checks:

- make sure Docker is running
- check whether port `5432` is already in use
- inspect the container logs with `docker logs pg-ticket`

### Application cannot connect to PostgreSQL

Checks:

- confirm `DB_URL`, `DB_USER`, and `DB_PASS`
- confirm the database name matches your container setup
- confirm PostgreSQL is listening on the expected port

## Docker issues

### `docker` command fails

Checks:

- start Docker Desktop or Colima
- run `docker ps` to confirm the daemon is available

### `docker compose` is not available

Checks:

- confirm your Docker installation includes compose support
- use a direct `docker run` fallback if compose files are not present in the branch you are using

## API key issues

### OpenAI requests fail immediately

Checks:

- confirm `TRIAGE_AI_MODE=openai`
- confirm `OPENAI_API_KEY` is exported in your current shell
- confirm your account has the required access and billing state

### Local model provider does not respond

Checks:

- confirm Ollama or LM Studio is actually running
- confirm `LLM_BASE_URL` points to the correct port
- confirm the selected local model exists and is loaded

## Frontend build issues

The Angular frontend is planned for Week 3 and may not be present in the branch you are using.

If the frontend branch exists and the build fails:

- confirm Node.js is installed
- confirm the correct npm dependencies are installed with `npm install`
- confirm Angular CLI is installed if the workspace expects `ng`

## AI provider connectivity issues

### Slow or inconsistent AI responses

Possible causes:

- provider latency
- local hardware limits
- model size mismatch for your machine
- network instability

Mitigations:

- start with the stub provider for development
- use smaller local models when testing locally
- keep fallback behavior enabled and visible

### Invalid or unusable AI output

Possible causes:

- prompt not constrained enough
- provider response formatting drift
- missing normalization and validation logic

Mitigations:

- enforce structured JSON output
- validate responses before persistence
- fall back safely when output cannot be trusted

## FAQ pointer

For common beginner questions such as local models, RAG, and production readiness, see [FAQ.md](FAQ.md).