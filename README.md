1) journal newday: добавляет шаблон с путыми временем и без описания, текущая дата, без аргументов
2) journal show: без аргументов, вывод календаря за текущий месяц



vera@hsg:~/IdeaProjects/ActivityJournal$ cd /home/vera/IdeaProjects/ActivityJournal

vera@hsg:~/IdeaProjects/ActivityJournal$ javac -cp "lib/picocli-4.7.6.jar" -d out $(find src -name "*.java")

vera@hsg:~/IdeaProjects/ActivityJournal$ echo "Main-Class: high.skill.girl.project.activity_journal.main.ActivityJournalApplication" > MANIFEST.MF

vera@hsg:~/IdeaProjects/ActivityJournal$ cd out

vera@hsg:~/IdeaProjects/ActivityJournal/out$ jar cfm ../ActivityJournal.jar ../MANIFEST.MF .

vera@hsg:~/IdeaProjects/ActivityJournal/out$ cd ..
