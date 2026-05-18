# Demo Script

This script gives you a clean walkthrough for presenting AI Helpdesk Mentor to learners, teammates, or potential employers.

## Demo goal

Show that this is both:

- a real AI product concept
- a structured learning project for modern software engineering

## Suggested demo length

- short version: 5 to 7 minutes
- standard version: 10 to 15 minutes

## Demo flow

### 1. Open with the product story

Say:

```text
AI Helpdesk Mentor is a staged project for building an AI-assisted support platform while learning backend architecture, AI orchestration, retrieval concepts, and a future Angular product UI.
```

Explain:

- it is not just a chatbot demo
- it teaches product architecture and engineering structure
- it separates implemented work from planned milestones

### 2. Show the learning roadmap

Walk through the staged build:

- Week 1 for backend fundamentals
- Week 2 for AI triage and retrieval foundations
- Week 3 for the Angular product UI
- future roadmap for gamification, enterprise mode, and deployment

### 3. Show the architecture

Open:

- [README.md](README.md)
- [docs/architecture.md](docs/architecture.md)
- [docs/SEQUENCE_DIAGRAMS.md](docs/SEQUENCE_DIAGRAMS.md)

Talk through:

- layered backend design
- provider abstraction
- retrieval path
- future frontend integration

### 4. Show the API design

Open [docs/API_REFERENCE.md](docs/API_REFERENCE.md).

Highlight:

- ticket creation
- status workflow
- AI triage endpoint
- problem+json error shape
- fallback behavior when AI fails

### 5. Show the AI design

Open:

- [docs/AI_ARCHITECTURE.md](docs/AI_ARCHITECTURE.md)
- [docs/PROMPTS.md](docs/PROMPTS.md)
- [docs/MODEL_PROVIDERS.md](docs/MODEL_PROVIDERS.md)

Explain:

- why the project uses provider abstraction
- why deterministic JSON matters
- why fallback logic is part of product quality
- how local and hosted models fit into the same architecture

### 6. Show the knowledge base path

Open:

- [docs/KNOWLEDGE_BASE.md](docs/KNOWLEDGE_BASE.md)
- [docs/ADDING_DOCUMENTS.md](docs/ADDING_DOCUMENTS.md)

Explain:

- how retrieval moves the product beyond naive prompting
- why chunking, metadata, and ranking matter

### 7. Close with the roadmap and branch strategy

Open:

- [SOLUTION_BRANCHES.md](SOLUTION_BRANCHES.md)
- [ROADMAP.md](ROADMAP.md)

Explain:

- learners can follow the project stage by stage
- solution branches prevent cognitive overload
- the full product grows from stable backend foundations rather than skipping to AI hype

## Optional runnable demo path

If a solution branch is available during the presentation:

1. start PostgreSQL or docker compose
2. run the Spring Boot backend
3. create a sample ticket
4. trigger AI triage
5. show the structured response and fallback behavior

## What a strong demo emphasizes

- architecture discipline
- educational clarity
- realistic AI product tradeoffs
- honest separation of implemented and planned work