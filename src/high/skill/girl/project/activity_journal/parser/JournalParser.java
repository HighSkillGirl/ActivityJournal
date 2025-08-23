package high.skill.girl.project.activity_journal.parser;

import high.skill.girl.project.activity_journal.pojo.JournalRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JournalParser {

    private static final Pattern DAY_INFO = Pattern.compile("\\[(\\p{L}+) (\\d+)\\]");
    private static final Pattern ACTIVITY_DETAILS = Pattern.compile("(\\d+)/(\\d+)h: (.+)");
    private static final Pattern LIFE = Pattern.compile("life: (.+)");

    public static List<JournalRecord> parse(String journalPath) {
        List<JournalRecord> journalRecordList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(journalPath))) {
            String weekday = null;
            int dayNumber = -1;
            List<JournalRecord.ActivityDetails> activityDetails = new ArrayList<>();
            String life = null;

            String line = bufferedReader.readLine();

            while (line != null) {
                Matcher dayInfoMatcher = DAY_INFO.matcher(line);
                Matcher activitiesMatcher = ACTIVITY_DETAILS.matcher(line);
                Matcher lifeMatcher = LIFE.matcher(line);

                if (dayInfoMatcher.find()) {
                    if (weekday != null) {
                        journalRecordList.add(new JournalRecord(weekday, dayNumber, activityDetails, life));
                        activityDetails = new ArrayList<>();
                        life = null;
                    }
                    weekday = dayInfoMatcher.group(1);
                    dayNumber = Integer.parseInt(dayInfoMatcher.group(2));
                }
                if (activitiesMatcher.find()) {
                    activityDetails.add(new JournalRecord.ActivityDetails(Integer.parseInt(activitiesMatcher.group(1)),
                                                                          Integer.parseInt(activitiesMatcher.group(2)),
                                                                          activitiesMatcher.group(3)
                    ));
                }
                if (lifeMatcher.find()) {
                    life = lifeMatcher.group(1);
                }

                line = bufferedReader.readLine();
            }

            if (weekday != null) {
                journalRecordList.add(new JournalRecord(weekday, dayNumber, activityDetails, life));
            }
        } catch (IOException e) {
            System.out.println("smth went wrong");
        }

        return journalRecordList;
    }
}
