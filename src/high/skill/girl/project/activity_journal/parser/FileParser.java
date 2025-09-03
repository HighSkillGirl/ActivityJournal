package high.skill.girl.project.activity_journal.parser;

import high.skill.girl.project.activity_journal.pojo.JournalRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private static final Pattern DAY_INFO = Pattern.compile("\\[(\\p{L}+) (\\d+)]");
    private static final Pattern ACTIVITY_DETAILS = Pattern.compile("(\\d+:\\d+h|live)\\s*-\\s+(.+)");

    public static List<JournalRecord> readAndParse(String journalPath) {
        List<JournalRecord> journalRecordList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(journalPath))) {
            String weekday = null;
            int dayNumber = -1;
            List<JournalRecord.ActivityDetails> activityDetails = new ArrayList<>();

            String line = bufferedReader.readLine();

            while (line != null) {
                Matcher dayInfoMatcher = DAY_INFO.matcher(line);
                Matcher activitiesMatcher = ACTIVITY_DETAILS.matcher(line);

                if (dayInfoMatcher.find()) {
                    if (weekday != null) {
                        journalRecordList.add(new JournalRecord(weekday, dayNumber, activityDetails));
                        activityDetails = new ArrayList<>();
                    }
                    weekday = dayInfoMatcher.group(1);
                    dayNumber = Integer.parseInt(dayInfoMatcher.group(2));
                }
                if (activitiesMatcher.find()) {
                    activityDetails.add(new JournalRecord.ActivityDetails(activitiesMatcher.group(1),
                                                                          activitiesMatcher.group(2)
                    ));
                }

                line = bufferedReader.readLine();
            }

            if (weekday != null) {
                journalRecordList.add(new JournalRecord(weekday, dayNumber, activityDetails));
            }
        } catch (IOException e) {
            System.out.println("smth went wrong");
        }

        return journalRecordList;
    }
}
