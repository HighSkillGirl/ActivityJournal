import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;

public class ActivityJournalApplication {
    public static void main (String[] weekJournal) {

        LocalDate today = LocalDate.now();

        String calendarPath = String.format("/home/vera/IdeaProjects/ActivityJournal/out/journal_%s_%d.txt", today.getMonth().toString().toLowerCase(), today.getYear());

        File calendarFile = new File(calendarPath);

        if (calendarFile.exists()) {
            StringBuilder readCalendar = readCalendarFromFile(calendarPath);
            markProgrammingDays(programmingDays, readCalendar, today.getDayOfMonth());
            writeUpdatedCalendarToFile(calendarPath, readCalendar.toString());
        } else {
            LocalDate monthBegin = today.minusDays(today.getDayOfMonth() - 1);
            int dayOfWeek = monthBegin.getDayOfWeek().getValue();
            StringBuilder calendar = createCalendar(monthBegin, dayOfWeek);
            markProgrammingDays(programmingDays, calendar, today.getDayOfMonth());
            writeUpdatedCalendarToFile(calendarPath, calendar.toString());

            for (int i = 0; i < 7; i++) {

                LocalDate dayToJournal = today;
                if (i != 0) {
                    dayToJournal = dayToJournal.minusDays(i);
                }
                String activity = i < weekJournal.length ? weekJournal[i] : "";

                String formattedOutput = String.format("%d %s: %s", dayToJournal.getDayOfMonth(), dayToJournal.getMonth().toString().toLowerCase(), activity);
                System.out.println(formattedOutput);
            }
        }
    }
}
