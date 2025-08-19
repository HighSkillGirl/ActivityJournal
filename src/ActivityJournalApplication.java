import java.io.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class ActivityJournalApplication {

    public static final LocalDate today = LocalDate.now();

    public static void main (String... activities) {

        String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File journalFile = new File(journalPath);

        if (journalFile.exists()) {
            StringBuilder readJournal = readCalendarFromFile(journalPath);
            String updatedJournal = journalToday(activities);
            writeJournalToFile(journalPath, readJournal.insert(0, updatedJournal).toString());
        } else {
            String monthJournal = journalToday(activities);
            writeJournalToFile(journalPath, monthJournal);
        }
    }

    private static String journalToday(String... activities) {
        int programmingHours = 4;
        int dealHours = 4;

        return String.format("[%s %s] %d/%dh: %s\n", today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru")),
                                                   today.getDayOfMonth(),
                                                   programmingHours + dealHours, 10,
                                                   activities[0]);
    }

    private static StringBuilder readCalendarFromFile(String journalPath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(journalPath))) {
            StringBuilder journalBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                journalBuilder.append(line);
                journalBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            return journalBuilder;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeJournalToFile(String journalPath, String journal) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            writer.print(journal);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }
}
