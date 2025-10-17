package high.skill.girl.project.activity_journal.pojo;

import java.util.List;

public record JournalRecord(String weekDayName, int dayOfMonth,
                            List<ActivityDetails> activityDetails)
{

    private static int dayInfoLength = 0;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String dayInfo = String.format("[%s %d] ", weekDayName, dayOfMonth);
        dayInfoLength = dayInfo.length();
        stringBuilder.append(dayInfo);

        for (int i = 0; i < activityDetails.size(); i++) {
            if (i == activityDetails.size() -1) dayInfoLength = -1;
            stringBuilder.append(activityDetails.get(i).toString());
        }

        return stringBuilder.toString();
    }

    public record ActivityDetails(String hoursInfo, String activitySummary)
    {
        @Override
        public String toString() {
            int hoursInfoInt = hoursInfo == null ? 0 : hoursInfo.length();
            return String.format("%s%s - %s\n%s", hoursInfo, " ".repeat(Math.max(0, (6 - hoursInfoInt) )), activitySummary, " ".repeat(Math.max(0, dayInfoLength)));
        }
    }

}
