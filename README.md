1) journal newday: добавляет шаблон с путыми временем и без описания, текущая дата, без аргументов
2) journal show: 
   2.1) без аргументов, вывод календаря за текущий месяц
   2.2) -c --count, вывод переданного числа записей по дням (-c 7 -> показать последние 7 дней, если не передано - показать все)



cd /home/vera/IdeaProjects/ActivityJournal

javac -cp "lib/picocli-4.7.6.jar" -d out $(find src -name "*.java")

cd out

jar cfm ../ActivityJournal.jar ../MANIFEST.MF .

cd ..
