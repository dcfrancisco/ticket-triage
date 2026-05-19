#!/usr/bin/env bash
set -euo pipefail

# Export project docs to PDF using pandoc.
# Requirements: pandoc installed locally (brew install pandoc), pdflatex or wkhtmltopdf for PDF engine.
# Usage: scripts/export-docs.sh

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCS_DIR="$ROOT_DIR/docs"
OUT_DIR="$ROOT_DIR/build/pdfs"

mkdir -p "$OUT_DIR"

# Grouped outputs only (minimize PDF count)
ROOT_GUIDE=(
  "$ROOT_DIR/README.md"
  "$ROOT_DIR/QUICKSTART.md"
  "$ROOT_DIR/INSTALLATION.md"
  "$ROOT_DIR/WEEK1_GUIDE.md"
  "$ROOT_DIR/WEEK2_GUIDE.md"
  "$ROOT_DIR/WEEK3_GUIDE.md"
  "$ROOT_DIR/SOLUTION_BRANCHES.md"
  "$ROOT_DIR/DEMO_SCRIPT.md"
  "$ROOT_DIR/TROUBLESHOOTING.md"
  "$ROOT_DIR/FAQ.md"
)
ARCHITECTURE_GUIDE=(
  "$DOCS_DIR/architecture.md"
  "$DOCS_DIR/SEQUENCE_DIAGRAMS.md"
  "$DOCS_DIR/DATA_MODEL.md"
  "$DOCS_DIR/API_REFERENCE.md"
)
AI_GUIDE=(
  "$DOCS_DIR/AI_ARCHITECTURE.md"
  "$DOCS_DIR/PROMPTS.md"
  "$DOCS_DIR/MODEL_PROVIDERS.md"
  "$DOCS_DIR/KNOWLEDGE_BASE.md"
  "$DOCS_DIR/ADDING_DOCUMENTS.md"
)
LEGACY_GUIDE=(
  "$DOCS_DIR/environment.md"
  "$DOCS_DIR/project-setup.md"
  "$DOCS_DIR/macos-setup.md"
  "$DOCS_DIR/learning-path.md"
  "$DOCS_DIR/weekend-0-prep.md"
  "$DOCS_DIR/weekend-1-core.md"
  "$DOCS_DIR/weekend-2-triage.md"
  "$DOCS_DIR/stretch-weekend.md"
  "$DOCS_DIR/master-scaffold-prompt.md"
  "$DOCS_DIR/index.md"
)

contains() {
  local seek="$1"; shift
  for item in "$@"; do
    [[ "$item" == "$seek" ]] && return 0
  done
  return 1
}

render_bundle() {
  local out_name="$1"; shift
  local inputs=()
  local input
  for input in "$@"; do
    [[ -f "$input" ]] && inputs+=("$input")
  done

  if (( ${#inputs[@]} )); then
    local out="$OUT_DIR/$out_name"
    echo "Rendering bundle -> $out"
    pandoc "${inputs[@]}" -o "$out"
  fi
}

render_bundle "ticket-triage-product-guide.pdf" "${ROOT_GUIDE[@]}"
render_bundle "ticket-triage-architecture-guide.pdf" "${ARCHITECTURE_GUIDE[@]}"
render_bundle "ticket-triage-ai-guide.pdf" "${AI_GUIDE[@]}"
render_bundle "ticket-triage-legacy-guide.pdf" "${LEGACY_GUIDE[@]}"

# Any additional markdowns not in bundles are rendered individually.
bundle_files=(
  "${ROOT_GUIDE[@]}"
  "${ARCHITECTURE_GUIDE[@]}"
  "${AI_GUIDE[@]}"
  "${LEGACY_GUIDE[@]}"
)
for dir in "$ROOT_DIR" "$DOCS_DIR"; do
  for path in "$dir"/*.md; do
    [[ -f "$path" ]] || continue
    if contains "$path" "${bundle_files[@]}"; then
      continue
    fi
    out="$OUT_DIR/$(basename "$path" .md).pdf"
    echo "Rendering extra doc $path -> $out"
    pandoc "$path" -o "$out"
  done
done

echo "Done. PDFs in $OUT_DIR"
