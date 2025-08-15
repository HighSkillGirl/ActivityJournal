import java.time.LocalDate;
import java.util.Arrays;

public class ActivityJournalApplication {
    public static void main (String[] weekJournal) { // разбивка по пробелам

        LocalDate today = LocalDate.now();

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
