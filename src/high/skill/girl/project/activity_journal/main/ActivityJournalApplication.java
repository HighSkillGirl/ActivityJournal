package high.skill.girl.project.activity_journal.main;

import high.skill.girl.project.activity_journal.parser.JournalParser;
import high.skill.girl.project.activity_journal.pojo.JournalNote;

import java.io.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityJournalApplication {

    private static final LocalDate today = LocalDate.now();

    public static void main (String... journalInfo) {

        String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File journalFile = new File(journalPath);

        if (journalFile.exists()) {
            List<JournalNote> journalNotes = JournalParser.parse(journalPath);

            JournalNote lastRecord = journalNotes.getFirst();
            if (lastRecord.dayOfMonth() == today.getDayOfMonth()) {
                System.out.println("За сегодняшний день запись уже есть");
                return;
            }
            JournalNote newRecord = journalToday(journalInfo);
            journalNotes.add(0, newRecord);
            writeJournalToFile(journalPath, journalNotes);
        } else {
//            String monthJournal = journalToday(journalInfo);
//            writeJournalToFile(journalPath, monthJournal);
        }
    }


    private static JournalNote journalToday(String... journalInfo) {
        String todayDayName = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru"));
        int todayAsDayOfMonth = today.getDayOfMonth();

        List<JournalNote.ActivityDetails> activities = new ArrayList<>();
        activities.add(new JournalNote.ActivityDetails(Integer.parseInt(journalInfo[0]), 6, journalInfo[1]));
        activities.add(new JournalNote.ActivityDetails(Integer.parseInt(journalInfo[2]), 4, journalInfo[3]));

        return new JournalNote(todayDayName, todayAsDayOfMonth, activities, journalInfo[4]);
    }



    private static void writeJournalToFile(String journalPath, List<JournalNote> updatedJournal) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            updatedJournal.forEach(writer::print);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }
}
