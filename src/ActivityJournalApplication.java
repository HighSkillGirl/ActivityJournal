import java.io.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class ActivityJournalApplication {

    private static final LocalDate today = LocalDate.now();
    private static final int HOURS_GOAL = 10;
    private static int CURRENT_HOURS_COUNTER = 0;

    public static void main (String... journalInfo) {

        String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File journalFile = new File(journalPath);

        if (journalFile.exists()) {
            StringBuilder readJournal = readCalendarFromFile(journalPath);
            String updatedJournal = journalToday(journalInfo);
            writeJournalToFile(journalPath, readJournal.insert(0, updatedJournal).toString());
        } else {
            String monthJournal = journalToday(journalInfo);
            writeJournalToFile(journalPath, monthJournal);
        }
    }

    private static String journalToday(String... journalInfo) {

        String hours = "";
        int summ = CURRENT_HOURS_COUNTER + Integer.parseInt(journalInfo[0]);
        if (CURRENT_HOURS_COUNTER == HOURS_GOAL || summ == HOURS_GOAL) {
            hours = String.valueOf(HOURS_GOAL);
        } else {
            hours = " " + summ;
        }
        return String.format("[%s %s] %s/%dh: %s\n", today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru")),
                                                   today.getDayOfMonth(),
                                                   hours, HOURS_GOAL,
                                                   journalInfo[1]);
    }

    private static StringBuilder readCalendarFromFile(String journalPath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(journalPath))) {
            StringBuilder journalBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            String[] splitted = line.split("/");
            int currentHours = Integer.parseInt(String.valueOf(splitted[0].charAt(splitted[0].length() - 1)));
            if (currentHours > 0) CURRENT_HOURS_COUNTER = currentHours;
            else CURRENT_HOURS_COUNTER = 10;

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
