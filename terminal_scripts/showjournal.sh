#!/bin/bash

# ------------------------
# Определяем месяц и год
# ------------------------
if [[ $# -eq 0 ]]; then
  MONTH=$(date +%B | tr '[:upper:]' '[:lower:]')  # текущий месяц
  YEAR=$(date +%Y)
elif [[ $# -eq 1 ]]; then
  # если только число -> последние N записей текущего месяца
  MONTH=$(date +%B | tr '[:upper:]' '[:lower:]')
  YEAR=$(date +%Y)
  COUNT=$1
elif [[ $# -eq 2 ]]; then
  # месяц + год -> весь файл
  MONTH=$1
  YEAR=$2
elif [[ $# -eq 3 ]]; then
  # месяц + год + количество записей
  MONTH=$1
  YEAR=$2
  COUNT=$3
else
  echo "Использование:"
  echo "  showjournal               # весь текущий месяц"
  echo "  showjournal 7             # 7 записей за текущий месяц"
  echo "  showjournal august 2025   # весь август 2025"
  echo "  showjournal august 2025 7 # 7 записей за август 2025"
  exit 1
fi

FILE="/home/vera/IdeaProjects/ActivityJournal/out/journal_${MONTH}_${YEAR}.txt"

if [[ ! -f "$FILE" ]]; then
  echo "Нет журнала с такими параметрами: $MONTH $YEAR"
  exit 1
fi

# ------------------------
# Вывод содержимого
# ------------------------
if [[ -z "$COUNT" ]]; then
  # если COUNT не задан -> показать весь файл
  cat "$FILE"
else
  # если COUNT задан -> вывести последние N записей
  awk -v n="$COUNT" '
    /^\[/ { if (++cnt > n) exit }
    { print }
  ' "$FILE"
fi
