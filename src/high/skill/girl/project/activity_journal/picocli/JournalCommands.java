package high.skill.girl.project.activity_journal.picocli;

import high.skill.girl.project.activity_journal.pojo.JournalRecord;
import picocli.CommandLine.Command;

import java.io.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(
        name = "journal",
        subcommands = {JournalCommands.NewDay.class, JournalCommands.ShowJournal.class}
)
public class JournalCommands implements Runnable {

    private static final LocalDate TODAY = LocalDate.now();

    @Override
    public void run() {}

    @Command(name = "newday")
    static class NewDay implements Runnable {

        @Override
        public void run() {
            String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt",
                    TODAY.getMonth().toString().toLowerCase(), TODAY.getYear());

            String fileAsString = readJournalFile(journalPath);

            String weekDayName = TODAY.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru"));
            int dayOfMonth = TODAY.getDayOfMonth();
            String dayInfo = String.format("[%s %d] ", weekDayName, dayOfMonth);

            if (fileAsString.contains(dayInfo)) {
                System.out.println("За сегодняшний день запись уже есть");
                return;
            }

            JournalRecord newRecord = recordingNewEmptyDay(weekDayName, dayOfMonth);
            writeJournalToFile(journalPath, fileAsString, newRecord);
        }
    }

    @Command(name = "show")
    static class ShowJournal implements Runnable {

        @Override
        public void run() {
            String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt",
                    TODAY.getMonth().toString().toLowerCase(), TODAY.getYear());

            String fileAsString = readJournalFile(journalPath);
            System.out.println(fileAsString);
        }
    }

    private static JournalRecord recordingNewEmptyDay(String weekDayName, int dayOfMonth) {
        List<JournalRecord.ActivityDetails> activities = new ArrayList<>(2);
        activities.add(new JournalRecord.ActivityDetails("0h", ""));
        activities.add(new JournalRecord.ActivityDetails("live", ""));
        return new JournalRecord(weekDayName, dayOfMonth, activities);
    }

    private static String readJournalFile(String journalPath) {
        StringBuilder sb = new StringBuilder();

        try (Reader reader = new BufferedReader(new FileReader(journalPath))) {
            int data;
            data = reader.read();

             while(data != -1) {
                sb.append( (char) data);
                try {
                    data = reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка во время работы с файлом: " + e.getLocalizedMessage());
        }
        return sb.toString();
    }

    private static void writeJournalToFile(String journalPath, String journalAsString, JournalRecord lastRecord) {
        String updatedString = lastRecord.toString() + journalAsString;
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            writer.print(updatedString);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }

}
