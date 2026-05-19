# API Reference

This is the human-readable API guide for AI Helpdesk Mentor. It is intended to complement Swagger, not replace it.

## Status note

The endpoint design below reflects the target staged implementation. The `main` branch currently documents the API shape and learning path rather than shipping the full backend source.

## Base path

```text
/api
```

## Ticket endpoints

### Create ticket

```text
POST /api/tickets
```

Purpose:

- create a new support ticket

Example request:

```json
{
  "subject": "Payment failed after subscription upgrade",
  "description": "A customer upgraded to the pro plan but still sees a billing error and cannot complete checkout.",
  "customerEmail": "customer@example.com"
}
```

Example response:

```json
{
  "id": "8be82639-635f-4f70-8cf0-298b2f2d5e34",
  "subject": "Payment failed after subscription upgrade",
  "description": "A customer upgraded to the pro plan but still sees a billing error and cannot complete checkout.",
  "customerEmail": "customer@example.com",
  "status": "NEW",
  "createdAt": "2026-05-19T10:30:00Z",
  "updatedAt": "2026-05-19T10:30:00Z"
}
```

### Get ticket by ID

```text
GET /api/tickets/{id}
```

Purpose:

- retrieve one ticket and its current state

### List tickets

```text
GET /api/tickets?status=&category=&priority=&page=0&size=20
```

Purpose:

- list tickets with optional filters and pagination

Example response shape:

```json
{
  "content": [
    {
      "id": "8be82639-635f-4f70-8cf0-298b2f2d5e34",
      "subject": "Payment failed after subscription upgrade",
      "status": "TRIAGED",
      "category": "BILLING",
      "priority": "P1"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

### Change ticket status

```text
POST /api/tickets/{id}/status
```

Purpose:

- move a ticket through its workflow state

Example request:

```json
{
  "status": "IN_PROGRESS",
  "allowRollback": false
}
```

## AI triage endpoint

### Triage a ticket

```text
POST /api/tickets/{id}/triage
```

Purpose:

- run AI-assisted triage for a ticket
- return a deterministic structured result

Example request:

```json
{
  "force": false
}
```

Example response:

```json
{
  "ticketId": "8be82639-635f-4f70-8cf0-298b2f2d5e34",
  "summary": "Customer cannot complete payment after upgrading their plan.",
  "category": "BILLING",
  "priority": "P1",
  "confidence": 0.88,
  "suggestedResponse": "Acknowledge the upgrade issue, confirm payment details, and review billing state.",
  "provider": "openai",
  "model": "gpt-4.1-mini",
  "createdAt": "2026-05-19T10:35:00Z",
  "updatedAt": "2026-05-19T10:35:00Z"
}
```

Fallback response example:

```json
{
  "ticketId": "8be82639-635f-4f70-8cf0-298b2f2d5e34",
  "summary": "Automatic triage was unavailable. Manual review is recommended.",
  "category": "OTHER",
  "priority": "P3",
  "confidence": 0.0,
  "suggestedResponse": null,
  "provider": "fallback",
  "model": "none"
}
```

## Similarity and knowledge endpoints

### Similar tickets

```text
GET /api/tickets/{id}/similar
```

Purpose:

- return similar tickets using vector search or a fallback similarity strategy

Status:

- planned advanced capability

## Error handling

The API should return consistent problem+json responses.

Example validation error:

```json
{
  "type": "https://example.com/problems/validation-error",
  "title": "Validation failed",
  "status": 400,
  "detail": "One or more fields are invalid.",
  "instance": "/api/tickets",
  "errors": [
    {
      "field": "subject",
      "message": "subject must be between 5 and 120 characters"
    }
  ]
}
```

Typical error scenarios:

- validation failure
- ticket not found
- invalid status transition
- provider configuration missing
- AI provider failure with fallback behavior

## Swagger note

Swagger should document the live contract once the implementation branch is running. This file exists so readers can understand the API design without needing the app running first.