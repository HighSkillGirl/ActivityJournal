package high.skill.girl.project.activity_journal.picocli;

import high.skill.girl.project.activity_journal.parser.FileParser;
import high.skill.girl.project.activity_journal.pojo.JournalRecord;
import picocli.CommandLine.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(
        name = "journal",
        subcommands = {JournalCommands.NewDay.class}
)
public class JournalCommands implements Runnable {

    private static final LocalDate TODAY = LocalDate.now();

    @Override
    public void run() {}

    private static JournalRecord recordingNewEmptyDay() {
        String weekDayName = TODAY.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru"));
        int dayOfMonth = TODAY.getDayOfMonth();

        List<JournalRecord.ActivityDetails> activities = new ArrayList<>(2);
        activities.add(new JournalRecord.ActivityDetails("0h", ""));
        activities.add(new JournalRecord.ActivityDetails("live", ""));
        return new JournalRecord(weekDayName, dayOfMonth, activities);
    }

    private static void writeJournalToFile(String journalPath, List<JournalRecord> journalRecordList) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            journalRecordList.forEach(writer::print);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }

    @Command(name = "newday")
    static class NewDay implements Runnable {

        @Override
        public void run() {
            String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt",
                                                TODAY.getMonth().toString().toLowerCase(), TODAY.getYear());

            File journalFile = new File(journalPath);
            List<JournalRecord> journalRecordList = new ArrayList<>();

            if (journalFile.exists()) {
                journalRecordList.addAll(FileParser.readAndParse(journalPath));
                JournalRecord lastRecord = journalRecordList.getFirst();
                if (lastRecord.dayOfMonth() == TODAY.getDayOfMonth()) {
                    System.out.println("За сегодняшний день запись уже есть");
                    return;
                }
            }

            JournalRecord newRecord = recordingNewEmptyDay();
            journalRecordList.addFirst(newRecord);
            writeJournalToFile(journalPath, journalRecordList);
        }
    }
}
