#!/usr/bin/env bash
set -euo pipefail

ICE_JAR="ice-3.7.11.jar"

if [ ! -f "$ICE_JAR" ]; then
  echo "Arquivo $ICE_JAR nao encontrado. Baixe com:"
  echo "wget https://repo1.maven.org/maven2/com/zeroc/ice/3.7.11/ice-3.7.11.jar"
  exit 1
fi

if [ ! -d "Demo" ]; then
  echo "Pasta Demo nao encontrada. Gerando arquivos Java com slice2java..."
  slice2java Printer.ice
fi

javac -cp ".:$ICE_JAR" Client.java Server.java PrinterI.java Demo/*.java

echo "Compilacao concluida com sucesso."
