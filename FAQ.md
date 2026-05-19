# FAQ

## Can this run without OpenAI?

Yes. The project is designed to support a stub provider for development and a local-model path for tools such as Ollama or LM Studio.

## Can I use local LLMs?

Yes. Local-model support is part of the documented Week 2 architecture. The exact runtime branch may vary, but the design expects a provider abstraction so local HTTP model servers can be used.

## Is this production-ready?

Not on `main`. The `main` branch is documentation-first. The architecture is intentionally production-flavored, but the runnable implementation is meant to land through staged solution branches.

## Why Spring Boot?

Spring Boot is a strong fit for this project because it gives learners a realistic backend stack with mature support for web APIs, validation, persistence, configuration, and testing.

## Why Angular?

Angular is planned for the frontend because it works well for structured, feature-rich applications with clear module boundaries, typed services, and dashboard-style product workflows.

## What is RAG?

RAG stands for retrieval-augmented generation. It means the AI uses retrieved knowledge-base context when forming a response, instead of relying only on the raw ticket text and model memory.

## Do I need pgvector to learn this project?

No. pgvector is the preferred vector path, but the learning design allows for fallback approaches so infrastructure details do not block the architecture lessons.

## Why not build the frontend first?

Because the backend and AI contracts should be stable before the UI tries to present them. This project teaches product growth in the order that usually creates less rework.

## What makes this different from a simple AI demo?

It treats AI as one subsystem inside a broader product architecture. That means validation, persistence, fallback behavior, clear APIs, retrieval, feedback loops, and future operational concerns are all part of the learning path.

## Is this only for beginners?

No. It is beginner-friendly, but it is also useful for developers who want a structured example of how to design a small AI product with clean architecture.