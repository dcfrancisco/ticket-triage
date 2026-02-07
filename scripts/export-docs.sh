#!/usr/bin/env bash
set -euo pipefail

# Export docs in docs/ to PDF using pandoc.
# Requirements: pandoc installed locally (brew install pandoc), pdflatex or wkhtmltopdf for PDF engine.
# Usage: scripts/export-docs.sh

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCS_DIR="$ROOT_DIR/docs"
OUT_DIR="$ROOT_DIR/build/pdfs"

mkdir -p "$OUT_DIR"

# List of markdown files to render individually
DOC_FILES=(
  "architecture.md"
  "learning-path.md"
  "macos-setup.md"
  "index.md"
  "weekend-0-prep.md"
  "weekend-1-core.md"
  "weekend-2-triage.md"
  "stretch-weekend.md"
  "environment.md"
  "project-setup.md"
)

# Groups for combined PDFs
ARCH_FILE="architecture.md"
INSTR_FILES=(
  "learning-path.md"
  "macos-setup.md"
  "index.md"
  "weekend-0-prep.md"
  "weekend-1-core.md"
  "weekend-2-triage.md"
  "stretch-weekend.md"
  "environment.md"
  "project-setup.md"
)

for file in "${DOC_FILES[@]}"; do
  input="$DOCS_DIR/$file"
  base="$(basename "$file" .md)"
  output="$OUT_DIR/${base}.pdf"
  if [[ ! -f "$input" ]]; then
    echo "Skipping missing file: $input" >&2
    continue
  fi
  echo "Rendering $input -> $output"
  pandoc "$input" -o "$output"
done

# Architecture-only PDF
arch_input="$DOCS_DIR/$ARCH_FILE"
if [[ -f "$arch_input" ]]; then
  arch_out="$OUT_DIR/architecture.pdf"
  echo "Rendering $arch_input -> $arch_out"
  pandoc "$arch_input" -o "$arch_out"
fi

# Instructions bundle PDF
instr_inputs=()
for file in "${INSTR_FILES[@]}"; do
  input="$DOCS_DIR/$file"
  [[ -f "$input" ]] && instr_inputs+=("$input")
done
if (( ${#instr_inputs[@]} )); then
  instr_out="$OUT_DIR/ticket-triage-instructions.pdf"
  echo "Rendering instructions bundle -> $instr_out"
  pandoc "${instr_inputs[@]}" -o "$instr_out"
fi

echo "Done. PDFs in $OUT_DIR"
