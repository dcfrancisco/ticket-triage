# Solution Branches

This project uses staged branches so learners can study one architectural milestone at a time.

## Branch strategy

The public learning model is organized around progressive implementation milestones.

### `solution-week1`

Purpose:

- backend foundation only
- Spring Boot setup
- ticket API
- validation
- Flyway
- PostgreSQL
- tests
- Docker support

Use this branch when you want to learn the backend fundamentals before any AI logic appears.

### `solution-week2`

Purpose:

- AI triage layer
- provider abstraction
- OpenAI adapter
- local model path
- fallback behavior
- confidence scoring
- knowledge base foundations

Use this branch when you want to study how AI features are layered on top of a stable backend.

### `solution-week3`

Purpose:

- Angular product UI
- ticket dashboard
- ticket detail page
- AI mentor panel
- admin concepts
- learner progress dashboard

Use this branch when you want to study product UI integration and user experience design.

### `solution-complete`

Purpose:

- complete learning project
- backend plus AI plus frontend
- strongest demo branch
- best branch for portfolio walkthroughs and presentations

Use this branch when you want the full end-to-end product experience.

## Current repository branch names

The repository currently exposes these branch names:

- `main`
- `solutions-week1`
- `solutions-week2`
- `solutions-stretch`

This means the branch model above is the intended public structure, while the currently available repository names still use the older `solutions-*` convention.

## Practical mapping today

- `solutions-week1` maps to the Week 1 backend milestone
- `solutions-week2` maps to the Week 2 AI milestone
- `solutions-stretch` currently covers advanced items that may later be split into `solution-week3` and `solution-complete`, depending on how the public release is packaged

## Recommended workflow

1. Read the docs on `main` first.
2. Switch to the branch that matches your target learning phase.
3. Compare the implementation against the architecture and guide documents.
4. Move forward one stage at a time rather than skipping directly to the final state.

## Why staged branches help

Staged branches make this repository more useful for learners because they:

- reduce cognitive overload
- make architectural growth visible
- let you compare each milestone in isolation
- support teaching, demos, and self-paced study