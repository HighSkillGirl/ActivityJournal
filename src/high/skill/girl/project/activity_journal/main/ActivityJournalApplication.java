package high.skill.girl.project.activity_journal.main;

import high.skill.girl.project.activity_journal.parser.JournalParser;
import high.skill.girl.project.activity_journal.pojo.JournalNote;

import java.io.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityJournalApplication {

    private static final LocalDate today = LocalDate.now();

    public static void main (String... journalInfo) {

        String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File journalFile = new File(journalPath);

        if (journalFile.exists()) {
            var journalNotes = JournalParser.parse(journalPath);
            journalNotes.forEach(System.out::print);
//            String updatedJournal = journalToday(journalInfo);
//            writeJournalToFile(journalPath, readJournal.insert(0, updatedJournal).toString());
        } else {
//            String monthJournal = journalToday(journalInfo);
//            writeJournalToFile(journalPath, monthJournal);
        }
    }



//    private static String journalToday(String... journalInfo) {
//        String journalNote = String.format("[%s %s] %d/6h: %s\n\t\t%d/4h: %s\n\t\tlife: %s\n",
//                today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru")),
//                today.getDayOfMonth(), Integer.parseInt(journalInfo[0]), journalInfo[1], Integer.parseInt(journalInfo[2]), journalInfo[3], journalInfo[4]);
//
//        System.out.println(journalNote);
//
//        return journalNote;
//    }



    private static void writeJournalToFile(String journalPath, String journal) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            writer.print(journal);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }
}
