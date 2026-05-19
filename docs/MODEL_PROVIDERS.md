# Model Providers

This document compares the model-provider paths planned for AI Helpdesk Mentor.

## Why provider choice matters

Different providers optimize for different things:

- setup speed
- cost
- latency
- output quality
- privacy and offline usage

There is no single correct provider for every learner or every environment.

## OpenAI

Best for:

- fast hosted setup
- strong default output quality
- quick structured-output experiments

Setup:

- set `TRIAGE_AI_MODE=openai`
- set `OPENAI_API_KEY`
- optionally set `OPENAI_MODEL`

Tradeoffs:

- paid API usage
- internet dependency
- external vendor dependency

## Ollama

Best for:

- local development
- privacy-sensitive experimentation
- offline or low-cost testing

Setup:

- run Ollama locally
- set `TRIAGE_AI_MODE=local`
- set `LLM_BASE_URL` to the Ollama endpoint
- set `LLM_MODEL` to the installed local model

Tradeoffs:

- output quality depends heavily on the chosen model and hardware
- may be slower on smaller machines

## LM Studio

Best for:

- developer-friendly desktop experimentation
- trying local models with a UI and local server endpoint

Setup:

- run LM Studio locally with API server enabled
- set `TRIAGE_AI_MODE=local`
- point `LLM_BASE_URL` to the LM Studio endpoint

Tradeoffs:

- great for local experimentation, but not a production deployment strategy by itself

## Anthropic future path

Best for:

- future provider expansion
- comparing model styles and instruction-following behavior

Status:

- planned, not currently represented in the repository structure

## Comparison summary

| Provider | Setup | Cost | Speed | Quality | Offline support |
| --- | --- | --- | --- | --- | --- |
| OpenAI | Easy | Paid | Fast hosted | Strong | No |
| Ollama | Medium | Low local cost | Hardware-dependent | Variable by model | Yes |
| LM Studio | Easy to medium | Low local cost | Hardware-dependent | Variable by model | Yes |
| Anthropic | Medium | Paid | Fast hosted | Strong | No |

## Recommendation by learning stage

- start with the stub provider for API and workflow development
- use OpenAI when you want the smoothest hosted AI path
- use Ollama or LM Studio when you want local experimentation or lower recurring cost

## Implementation status

- OpenAI and local-provider concepts are part of the Week 2 design
- Anthropic is a future extension path