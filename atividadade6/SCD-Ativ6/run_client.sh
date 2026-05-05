#!/usr/bin/env bash
set -euo pipefail

HOST="${1:-127.0.0.1}"
PORT="${2:-11000}"
OBJECT_NAME="${3:-SimplePrinter}"
TEXT="${4:-Hello from Goiania!}"

java -cp ".:ice-3.7.11.jar" Client "$HOST" "$PORT" "$OBJECT_NAME" "$TEXT"
