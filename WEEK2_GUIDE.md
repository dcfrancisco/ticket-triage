# Week 2 Guide

Week 2 adds the AI intelligence layer. This is where the project stops being a standard CRUD backend and starts becoming an AI product.

## Week 2 objective

Add AI-assisted triage while keeping the system stable, deterministic, and easy to reason about.

The major goals are:

- ticket classification
- summarization
- severity or priority suggestion
- provider abstraction
- OpenAI integration
- optional local model support
- fallback strategies
- confidence scoring
- knowledge base and retrieval foundations
- a feedback learning loop

## AI triage concepts

An AI triage workflow usually takes an incoming support ticket and produces a structured interpretation.

Typical outputs include:

- summary
- category
- priority
- confidence
- suggested response

This matters because a useful AI system does more than generate text. It produces data the rest of the product can trust and act on.

## Prompt engineering in this project

The project should favor deterministic, schema-oriented prompts over vague open-ended prompts.

Good prompt design here means:

- asking for strict JSON output
- constraining category and priority values
- keeping summaries concise
- separating classification from explanation when helpful

Why this matters:

- API consumers need predictable shapes
- persistence works better with bounded outputs
- fallback behavior is easier to design

## Provider abstraction

Do not couple the application service directly to one model vendor.

Instead, define an application port for AI triage and place provider-specific logic behind adapters.

Typical implementations:

- stub provider for tests and demos
- OpenAI provider for hosted usage
- local HTTP provider for Ollama, LM Studio, or similar tools

Why this matters:

- tests can run without external AI dependencies
- local experimentation becomes possible
- future model vendors can be added without rewriting business logic

## OpenAI integration

OpenAI is a strong hosted option for the Week 2 phase because it is easy to integrate and usually produces good structured results.

Important design choices:

- keep API keys in environment variables
- isolate request and response mapping in an adapter
- treat provider failure as a normal case, not a surprise

## Fallback strategies

AI systems fail in real life. The project should respond safely when a provider is unavailable or returns unusable content.

A safe fallback for triage can look like:

- category `OTHER`
- priority `P3`
- confidence `0.0`
- optional note that fallback logic was used

Why this matters:

- the API stays available even when AI is degraded
- product behavior remains understandable
- debugging becomes easier because failure has a known shape

## Confidence scoring

Confidence scores are useful when treated honestly.

They should:

- help users decide whether to trust an AI result
- support future review or escalation workflows
- avoid pretending to be mathematically rigorous when they are not

In early versions, a confidence value is best treated as a product hint rather than a hard truth.

## Knowledge base concepts

Week 2 introduces the idea that tickets should eventually be enriched by internal knowledge, not just model priors.

Foundational concepts:

- documents are ingested and normalized
- content is chunked into retrieval-friendly pieces
- chunks can be embedded
- relevant chunks can be retrieved during explanation or resolution generation

This is the bridge to retrieval-augmented generation.

## Embeddings and vector search

Embeddings turn text into vectors that allow similarity search.

Why that matters here:

- similar tickets can be discovered
- support documentation can be matched to ticket text
- the future mentor panel can explain decisions using retrieved context

The project prefers pgvector when available, but it should also tolerate a fallback path so the learning experience does not depend entirely on one infrastructure choice.

## RAG basics

Retrieval-augmented generation means the model is guided by retrieved context instead of relying only on what it already knows.

In this project, that can support:

- better suggested responses
- grounded troubleshooting guidance
- beginner-friendly explanations of why a ticket was categorized a certain way

## Feedback learning loop

A healthy AI product learns from human feedback.

Possible signals include:

- triage accepted or overridden
- suggested response edited by a human
- category corrections
- successful resolutions linked back to the ticket

This is not just analytics. It becomes future training, evaluation, and prompt improvement input.

## Week 2 architecture mindset

The AI feature should feel like a carefully designed subsystem, not a random extra controller method.

Good signs:

- provider logic is isolated
- prompts are versionable
- outputs are persisted consistently
- failure modes are explicit
- future retrieval can fit naturally into the design

## Implemented vs planned

Current repository status:

- `main` documents the Week 2 design and expected architecture
- runnable AI triage implementation is expected in staged solution branches rather than on `main`

For deeper AI design details, see [docs/AI_ARCHITECTURE.md](docs/AI_ARCHITECTURE.md) and [docs/PROMPTS.md](docs/PROMPTS.md).