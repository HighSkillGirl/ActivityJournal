import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class ActivityJournalApplication {
    public static void main (String... daysToJournal) {

        LocalDate today = LocalDate.now();

        String journalPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File journalFile = new File(journalPath);

        if (journalFile.exists()) {
            // файл журнала за текущий месяц есть
            // делаем запись за сегодняшний день - сколько часов учебы и дел, короткое саммари основных трудозатрат
            // обновляем файл, проверяем, что изменения сохранились
        } else {
            String monthJournal = createCurrentMonthJournal(today, daysToJournal);
            writeJournalToFile(journalPath, monthJournal);
        }
    }

    private static String createCurrentMonthJournal(LocalDate dayToJournal, String... daysToJournal) {
        int programmingHours = 4;
        int dealHours = 4;

        return String.format("[%s %s] %d / %d: %s", dayToJournal.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru")),
                                                    dayToJournal.getDayOfMonth(),
                                                    programmingHours + dealHours, 10,
                                                    daysToJournal[0]);
    }

    private static void writeJournalToFile(String journalPath, String journal) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(journalPath), true)) {
            writer.print(journal);
        } catch (FileNotFoundException e) {
            System.out.println("Что-то пошло не так");
        }
    }
}
