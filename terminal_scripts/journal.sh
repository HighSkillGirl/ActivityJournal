#!/bin/bash
javac -cp /home/vera/IdeaProjects/ActivityJournal/lib/picocli-4.7.6.jar -d out $(find /home/vera/IdeaProjects/ActivityJournal/src -name "*.java")
java -cp "/home/vera/IdeaProjects/ActivityJournal/out:/home/vera/IdeaProjects/ActivityJournal/lib/picocli-4.7.6.jar" \
  high.skill.girl.project.activity_journal.main.ActivityJournalApplication "$@"