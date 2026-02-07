# [stretch] Stretch Weekend — Extras

## Goals
- Explore similarity search, platform hardening, and delivery.

## Options (pick a subset)
- Similarity
  - Add embeddings table or pgvector column; feature-flagged.
  - Implement `EmbeddingPort` with stub/text-sim fallback and pgvector adapter.
  - GET /api/tickets/{id}/similar (paginated).
- Security
  - API key or basic auth for admin/test use.
  - Rate limiting at edge (if using gateway) or simple request filter.
- Platform (compose → k8s)
  - Start from the existing docker compose setup; add image publishing if needed.
  - Create Kubernetes manifests or Helm chart (Deployment, Service, ConfigMap, Secret); test on kind/minikube.
  - Add health/readiness probes; set resource requests/limits.
- Deployment targets (choose one to practice)
  - AWS: ECS with Fargate (compose convert → ECS) or EKS if already using k8s.
  - Azure: Azure Container Apps for simpler path, or AKS if k8s manifests already exist.
  - GCP: Cloud Run (great from container image) or GKE if k8s manifests are ready.
- CI/CD
  - GitHub Actions: build, test (with Testcontainers), optionally docker build and push.
- Observability
  - Add actuator; consider minimal metrics and structured logs shipping.

## Deliverables (choose)
- Working similarity endpoint (if chosen) and migration applied.
- Kubernetes manifests/Helm validated locally (compose still supported for local dev).
- CI workflow running tests; optional image build/push to chosen cloud registry.
