#!/usr/bin/env bash
set -euo pipefail

PORT="${1:-11000}"
OBJECT_NAME="${2:-SimplePrinter}"

java -cp ".:ice-3.7.11.jar" Server "$PORT" "$OBJECT_NAME"
