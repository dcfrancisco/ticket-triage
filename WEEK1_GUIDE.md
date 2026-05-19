# Week 1 Guide

Week 1 is where this project becomes a real backend rather than an idea. The focus is not AI yet. The goal is to build a stable support-ticket foundation with the kind of structure that still makes sense once AI features arrive.

## Week 1 objective

Build the functional backend foundation:

- Spring Boot application structure
- REST API
- DTO validation
- persistence with JPA and PostgreSQL
- schema management with Flyway
- problem+json error handling
- tests and local Docker support

## Why this week matters

Many AI demos fail because the surrounding product is weak. If ticket creation, validation, persistence, and status management are messy, adding a model on top only makes the mess harder to debug.

Week 1 creates the stable contract that Week 2 AI workflows depend on.

## Architecture shape

The recommended backend uses four layers.

### Controller layer

Controllers accept HTTP requests and return HTTP responses. They should:

- map JSON to DTOs
- trigger application services
- translate service output into response payloads
- avoid business logic

Why this matters:

- controllers stay easy to test
- validation boundaries stay predictable
- business rules remain reusable outside HTTP

### Application service layer

Application services orchestrate use cases such as:

- creating a ticket
- fetching a ticket
- listing tickets
- changing ticket status

Why this matters:

- it centralizes workflow logic
- it provides a natural place for ports and adapters later
- it keeps business flows readable

### Domain layer

The domain layer owns the model and core rules, especially status transitions.

Examples:

- a ticket starts in `NEW`
- a ticket can move to `TRIAGED`
- a ticket can later move to `IN_PROGRESS` and `DONE`
- invalid transitions should fail explicitly

Why this matters:

- the core business rules survive framework changes
- status logic does not leak across controllers and repositories

### Repository and infrastructure layer

Infrastructure handles JPA entities, Spring Data repositories, and persistence adapters.

Why this matters:

- persistence concerns stay separated from use-case orchestration
- future AI, vector, and retrieval adapters can follow the same pattern

## DTOs and API contracts

DTOs are important because API payloads should not expose your internal domain model directly.

Common Week 1 DTOs include:

- create-ticket request
- ticket response
- status-update request
- paginated ticket list response

Why DTOs matter:

- they let you validate inputs at the edge
- they reduce coupling between HTTP and domain code
- they make future API versioning easier

## Validation

Week 1 should validate inputs before business logic runs.

Typical rules:

- subject length must be reasonable
- description must not be too short
- customer email must be a valid email address
- status updates must use allowed enum values

Why validation matters:

- bad requests fail early and clearly
- downstream services receive clean inputs
- the API behaves more like a real product and less like a prototype

## Flyway and schema evolution

Use Flyway to manage database schema changes from the beginning.

Typical early migration:

- `tickets` table with UUID primary key
- subject, description, customer email
- status
- timestamps

Why Flyway matters:

- schema changes become repeatable
- local, test, and future deployment environments stay aligned
- Week 2 can add triage tables safely rather than ad hoc

## Exception handling and problem+json

Week 1 should not return random stack traces or unstructured error messages.

Use a consistent problem+json style response with:

- `type`
- `title`
- `status`
- `detail`
- `instance`
- `errors[]` for validation details

Why this matters:

- clients can handle errors consistently
- debugging becomes easier
- the API feels production-flavored even while still educational

## Testing strategy

Week 1 should include at least three testing layers.

### Domain tests

Use unit tests for status transition rules and core business behavior.

### Application tests

Test use-case orchestration, especially not-found paths and state-change behavior.

### Repository and controller tests

Use integration tests to verify:

- repository persistence
- migration correctness
- controller validation and response shapes

Testcontainers is a good fit because it exercises PostgreSQL instead of an unrealistic in-memory substitute.

## Recommended endpoints for Week 1

- `POST /api/tickets`
- `GET /api/tickets/{id}`
- `GET /api/tickets`
- `POST /api/tickets/{id}/status`

These endpoints give you enough surface area to establish:

- validation
- persistence
- pagination basics
- lifecycle rules
- error handling

## Deliverable mindset

At the end of Week 1, you should not aim for AI yet. You should aim for a backend that is boring in the best way:

- predictable
- testable
- easy to extend
- clear enough that Week 2 can focus on intelligence rather than cleanup

## Implemented vs planned

Current repository status:

- `main` documents this architecture and learning path
- the Week 1 runnable implementation is expected to live in the staged solution branches

For the broader system context, see [docs/architecture.md](docs/architecture.md).