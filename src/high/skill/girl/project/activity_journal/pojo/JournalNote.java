package high.skill.girl.project.activity_journal.pojo;

import java.util.List;

public record JournalNote(String weekDayName, int dayOfMonth,
                          List<ActivityDetails> activityDetails,
                          String lifeActivitySummary)
{

    private static int dayInfoLength = 0;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String dayInfo = String.format("[%s %d] ", weekDayName, dayOfMonth);
        dayInfoLength = dayInfo.length();
        stringBuilder.append(dayInfo);

        for (ActivityDetails activity : activityDetails) {
            stringBuilder.append(activity.toString());
        }

        stringBuilder.append(String.format("life: %s\n", lifeActivitySummary));

        return stringBuilder.toString();
    }

    public record ActivityDetails(int currentHours, int goalHours, String activitySummary)
    {
        @Override
        public String toString() {
            return String.format("%d/%dh: %s\n%s", currentHours, goalHours, activitySummary, " ".repeat(Math.max(0, dayInfoLength)));
        }
    }

}
