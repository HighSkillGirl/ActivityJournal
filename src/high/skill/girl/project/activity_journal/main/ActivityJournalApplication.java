package high.skill.girl.project.activity_journal.main;

import high.skill.girl.project.activity_journal.parser.JournalParser;
import high.skill.girl.project.activity_journal.pojo.JournalRecord;

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

        List<JournalRecord> journalRecordList = new ArrayList<>();

        if (journalFile.exists()) {
            journalRecordList.addAll(JournalParser.parse(journalPath));
            JournalRecord lastRecord = journalRecordList.getFirst();
            if (lastRecord.dayOfMonth() == today.getDayOfMonth()) {
                System.out.println("За сегодняшний день запись уже есть");
                return;
            }
        }

        JournalRecord newRecord = makeRecordingToday(journalInfo);
        journalRecordList.addFirst(newRecord);
        writeJournalToFile(journalPath, journalRecordList);
    }

    private static JournalRecord makeRecordingToday(String... journalInfo) {
        String weekDayName = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru"));
        int dayOfMonth = today.getDayOfMonth();

        List<JournalRecord.ActivityDetails> activities = new ArrayList<>();
        activities.add(new JournalRecord.ActivityDetails(Integer.parseInt(journalInfo[0]), 6, journalInfo[1]));
        activities.add(new JournalRecord.ActivityDetails(Integer.parseInt(journalInfo[2]), 4, journalInfo[3]));

        return new JournalRecord(weekDayName, dayOfMonth, activities, journalInfo.length > 4 ? journalInfo[4] : "");
    }

    private static void writeJournalToFile(String journalPath, List<JournalRecord> updatedJournal) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            updatedJournal.forEach(writer::print);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }
}
