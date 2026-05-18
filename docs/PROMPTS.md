# Prompts

This document describes the prompt design strategy for AI Helpdesk Mentor. Some prompts are planned rather than implemented today, but documenting them early makes the AI architecture easier to evolve and review.

## Prompt design principles

- prefer structured outputs over freeform prose
- keep instructions explicit and bounded
- avoid mixing too many goals in one prompt
- separate classification from explanation when clarity improves
- keep prompts easy to version and compare

## Ticket classifier prompt

Purpose:

- classify the ticket into a supported category
- suggest a priority level
- return a confidence value

Expected output shape:

```json
{
  "summary": "Short summary of the ticket",
  "category": "TECHNICAL",
  "priority": "P1",
  "confidence": 0.82,
  "suggestedResponse": "Optional response"
}
```

Prompt shape:

```text
You are an AI support triage assistant.
Read the ticket and return valid JSON only.
Choose category from: BILLING, TECHNICAL, ACCOUNT, FEATURE_REQUEST, OTHER.
Choose priority from: P0, P1, P2, P3.
Write a concise summary no longer than 500 characters.
Return confidence as a number between 0 and 1.
```

## Summarizer prompt

Purpose:

- generate a short summary for the ticket detail page and support queue

Prompt direction:

```text
Summarize the support issue in one concise paragraph suitable for an internal support dashboard.
Do not invent facts not present in the ticket.
```

## Mentor prompt

Purpose:

- explain the triage result to a learner or junior support engineer

Planned use:

- AI mentor panel in Week 3

Prompt direction:

```text
Explain why this ticket was categorized this way.
Use beginner-friendly language.
If knowledge-base context is available, cite the most relevant reasoning.
```

## Root cause prompt

Purpose:

- suggest likely root-cause areas for technical issues

Prompt direction:

```text
Given the ticket and any retrieved troubleshooting context, list the most likely root-cause areas.
Keep the response grounded in the provided information.
```

## Beginner explanation prompt

Purpose:

- convert a support issue into a learning explanation

Prompt direction:

```text
Explain this issue to a beginner developer.
Describe the likely concepts involved, common causes, and what to inspect first.
Avoid unnecessary jargon.
```

## Implementation status

- classifier and summarizer prompts are core Week 2 concepts
- mentor, root-cause, and beginner explanation prompts are planned extensions for later stages