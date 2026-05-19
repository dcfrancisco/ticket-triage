# Sequence Diagrams

These diagrams describe the target runtime flows for the staged implementation. They are useful both for architecture review and for teaching how requests move through the system.

## Ticket creation flow

```mermaid
sequenceDiagram
  participant Client
  participant Controller
  participant Service
  participant Domain
  participant Repository
  participant DB

  Client->>Controller: POST /api/tickets
  Controller->>Controller: Validate DTO
  Controller->>Service: createTicket(request)
  Service->>Domain: create ticket aggregate
  Domain-->>Service: validated ticket
  Service->>Repository: save(ticket)
  Repository->>DB: insert ticket row
  DB-->>Repository: saved record
  Repository-->>Service: saved ticket
  Service-->>Controller: ticket response
  Controller-->>Client: 201 Created
```

## AI triage flow

```mermaid
sequenceDiagram
  participant Client
  participant Controller
  participant Service
  participant TicketRepo
  participant AiPort
  participant Provider
  participant TriageRepo
  participant DB

  Client->>Controller: POST /api/tickets/{id}/triage
  Controller->>Service: triageTicket(id, force)
  Service->>TicketRepo: findById(id)
  TicketRepo->>DB: select ticket
  DB-->>TicketRepo: ticket row
  TicketRepo-->>Service: ticket
  Service->>TriageRepo: findExistingByTicketId(id)
  TriageRepo->>DB: select triage result
  DB-->>TriageRepo: optional triage result
  TriageRepo-->>Service: existing or empty
  alt Existing triage and force is false
    Service-->>Controller: existing triage result
    Controller-->>Client: 200 OK
  else New triage or force retriage
    Service->>AiPort: generateTriage(ticket)
    AiPort->>Provider: provider request
    Provider-->>AiPort: model response or failure
    AiPort-->>Service: normalized triage or fallback
    Service->>TriageRepo: save(triageResult)
    TriageRepo->>DB: upsert triage result
    DB-->>TriageRepo: persisted result
    TriageRepo-->>Service: stored triage
    Service-->>Controller: triage response
    Controller-->>Client: 200 OK
  end
```

## Knowledge base retrieval flow

```mermaid
sequenceDiagram
  participant Service
  participant Retriever
  participant Embedder
  participant VectorStore
  participant PromptBuilder
  participant Model

  Service->>Retriever: retrieveContext(ticketText)
  Retriever->>Embedder: embed(query text)
  Embedder-->>Retriever: query vector
  Retriever->>VectorStore: similarity search
  VectorStore-->>Retriever: ranked chunks
  Retriever-->>Service: retrieval context
  Service->>PromptBuilder: build grounded prompt
  PromptBuilder-->>Service: final prompt
  Service->>Model: execute prompt
  Model-->>Service: grounded response
```

## Frontend interaction flow

```mermaid
sequenceDiagram
  participant User
  participant AngularUI
  participant ApiClient
  participant Backend
  participant AI

  User->>AngularUI: Open ticket detail
  AngularUI->>ApiClient: fetch ticket data
  ApiClient->>Backend: GET /api/tickets/{id}
  Backend-->>ApiClient: ticket payload
  ApiClient-->>AngularUI: normalized response
  User->>AngularUI: Request AI triage or explanation
  AngularUI->>ApiClient: trigger triage
  ApiClient->>Backend: POST /api/tickets/{id}/triage
  Backend->>AI: run triage workflow
  AI-->>Backend: structured triage output
  Backend-->>ApiClient: triage response
  ApiClient-->>AngularUI: update UI state
  AngularUI-->>User: show summary, priority, and guidance
```