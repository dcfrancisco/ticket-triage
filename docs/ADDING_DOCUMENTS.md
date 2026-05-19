# Adding Documents

This guide explains how to think about adding knowledge-base source material to AI Helpdesk Mentor.

## What kinds of documents belong in the knowledge base

Good candidates include:

- markdown guides
- PDF manuals or runbooks
- troubleshooting guides
- support process documents
- API documentation

The common rule is simple: add documents that can help explain, classify, troubleshoot, or resolve support issues.

## Markdown documents

Markdown is usually the easiest format to ingest.

Recommended practices:

- use clear headings
- keep sections focused
- avoid duplicating the same guidance in too many files
- include product or platform labels when relevant

## PDFs

PDFs are useful when the source material already exists in that format, but they often need extra cleanup.

Recommended practices:

- prefer text-based PDFs over scanned images
- review extraction quality after ingestion
- break large PDFs into meaningful chunks if needed

## Troubleshooting guides

Troubleshooting guides are especially valuable because they map well to support-ticket workflows.

Good guides usually include:

- symptoms
- likely causes
- diagnostic steps
- resolution steps
- escalation criteria

## Support documents

Internal support notes, runbooks, and issue-handling guidelines can be powerful retrieval sources if they are current and clearly structured.

Add metadata when possible, such as:

- product area
- severity level
- internal or public audience
- version or last-reviewed date

## API docs

API documentation is useful when the AI mentor needs to explain integration failures, status codes, request validation, or endpoint behavior.

Helpful API documentation includes:

- endpoint purpose
- request and response examples
- common error cases
- authentication notes

## Quality guidelines

Before adding a document, ask:

- is it accurate
- is it current
- is it specific enough to help support work
- can it be chunked cleanly
- does it contain sensitive material that should not be indexed

## Implementation status

This guide documents the intended ingestion workflow. The actual ingestion pipeline is planned, not present on `main` today.