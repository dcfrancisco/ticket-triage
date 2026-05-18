# Week 3 Guide

Week 3 turns the backend and AI workflows into a product experience. The goal is not to add a generic frontend. The goal is to design a UI that makes the support workflow, AI reasoning, and learning journey visible.

## Week 3 objective

Build the frontend product layer with Angular.

Planned areas:

- ticket dashboard
- ticket detail page
- AI mentor panel
- knowledge base admin UI
- learner progress dashboard

## Angular architecture

The frontend should be organized around product areas rather than a pile of pages.

A clean structure usually includes:

- feature modules or feature folders
- shared UI components
- API services for backend communication
- models matching backend contracts
- route-based screens for dashboards and ticket views

Why this matters:

- the UI can grow without becoming tangled
- AI and support features stay discoverable
- learners can connect backend concepts to visible product behavior

## Component structure

A likely Week 3 component set includes:

- dashboard shell
- ticket list or board
- ticket detail view
- status update controls
- triage summary card
- AI mentor panel
- knowledge document list and detail views
- learner progress widgets

The important design choice is to keep presentational components separated from data-fetching and orchestration logic when possible.

## API integration

The Angular client should treat the backend as a clear contract.

That means:

- using typed API services
- centralizing base URL and environment settings
- handling loading, empty, and error states explicitly
- surfacing problem+json responses in a user-friendly way

Why this matters:

- the UI stays honest about backend state
- debugging integration issues becomes simpler
- future authentication and multi-user flows become easier to add

## AI mentor UI concepts

The AI mentor panel is one of the parts that makes this project more than a ticketing clone.

It can eventually help users:

- understand why the AI picked a category or priority
- see relevant knowledge-base context
- learn debugging or support concepts from the ticket
- review alternative resolutions or next steps

This is where the project becomes both a product and a learning experience.

## Dashboard design

The dashboard should answer practical workflow questions first.

Examples:

- what tickets need attention now
- which items were AI-triaged recently
- where confidence is low
- what categories are trending
- where learners are improving over time

The dashboard is valuable when it helps both support operations and learning progress.

## Admin UI concepts

The knowledge base admin interface should eventually support:

- document ingestion
- metadata review
- chunk inspection
- embedding and retrieval visibility
- prompt or source debugging

This helps learners understand that AI products need operational tooling, not just a single chat box.

## Learner progress dashboard

One unique direction for this project is a learner-facing product surface.

Possible progress indicators:

- tickets completed
- categories mastered
- streaks or practice consistency
- mentor explanations reviewed
- challenge tickets solved

This area is planned, not implemented on `main` today.

## Week 3 design mindset

The frontend should communicate three things clearly:

- product workflow
- AI assistance
- learning progression

If the UI only mirrors CRUD endpoints, it misses the main opportunity of the project.

## Implemented vs planned

Current repository status:

- the Angular frontend is planned for Week 3
- there is no committed Angular workspace on `main` today
- the Week 3 branch strategy is documented so the frontend can land cleanly later

For system-wide context, see [docs/architecture.md](docs/architecture.md).