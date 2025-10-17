1) journal newday: добавляет шаблон с путыми временем и без описания, текущая дата, без аргументов
2) journal show: без аргументов, вывод календаря за текущий месяц



cd /home/vera/IdeaProjects/ActivityJournal

javac -cp "lib/picocli-4.7.6.jar" -d out $(find src -name "*.java")

cd out

jar cfm ../ActivityJournal.jar ../MANIFEST.MF .

cd ..
