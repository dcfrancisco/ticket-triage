#!/usr/bin/env bash
set -euo pipefail

# Export docs in docs/ to PDF using pandoc.
# Requirements: pandoc installed locally (brew install pandoc), pdflatex or wkhtmltopdf for PDF engine.
# Usage: scripts/export-docs.sh

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCS_DIR="$ROOT_DIR/docs"
OUT_DIR="$ROOT_DIR/build/pdfs"

mkdir -p "$OUT_DIR"

# Grouped outputs only (minimize PDF count)
ARCH_FILE="architecture.md"
LEARNER_GUIDE=(
  "environment.md"
  "project-setup.md"
  "macos-setup.md"
  "learning-path.md"
  "weekend-0-prep.md"
  "weekend-1-core.md"
  "weekend-2-triage.md"
  "master-scaffold-prompt.md"
)
STRETCH_GUIDE=(
  "stretch-weekend.md"
)

contains() {
  local seek="$1"; shift
  for item in "$@"; do
    [[ "$item" == "$seek" ]] && return 0
  done
  return 1
}

arch_input="$DOCS_DIR/$ARCH_FILE"
if [[ -f "$arch_input" ]]; then
  arch_out="$OUT_DIR/architecture.pdf"
  echo "Rendering $arch_input -> $arch_out"
  pandoc "$arch_input" -o "$arch_out"
fi

# Learner guide bundle
learner_inputs=()
for file in "${LEARNER_GUIDE[@]}"; do
  input="$DOCS_DIR/$file"
  [[ -f "$input" ]] && learner_inputs+=("$input")
done
if (( ${#learner_inputs[@]} )); then
  instr_out="$OUT_DIR/ticket-triage-learner-guide.pdf"
  echo "Rendering learner guide bundle -> $instr_out"
  pandoc "${learner_inputs[@]}" -o "$instr_out"
fi

# Stretch guide bundle
stretch_inputs=()
for file in "${STRETCH_GUIDE[@]}"; do
  input="$DOCS_DIR/$file"
  [[ -f "$input" ]] && stretch_inputs+=("$input")
done
if (( ${#stretch_inputs[@]} )); then
  stretch_out="$OUT_DIR/ticket-triage-stretch.pdf"
  echo "Rendering stretch guide -> $stretch_out"
  pandoc "${stretch_inputs[@]}" -o "$stretch_out"
fi

# Any additional markdowns not in bundles are rendered individually
bundle_files=("$ARCH_FILE" "${LEARNER_GUIDE[@]}" "${STRETCH_GUIDE[@]}")
for path in "$DOCS_DIR"/*.md; do
  [[ -f "$path" ]] || continue
  name="$(basename "$path")"
  if contains "$name" "${bundle_files[@]}"; then
    continue
  fi
  out="$OUT_DIR/$(basename "$name" .md).pdf"
  echo "Rendering extra doc $path -> $out"
  pandoc "$path" -o "$out"
done

echo "Done. PDFs in $OUT_DIR"
