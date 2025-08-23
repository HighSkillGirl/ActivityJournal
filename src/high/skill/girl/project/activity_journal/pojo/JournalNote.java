package high.skill.girl.project.activity_journal.pojo;

import java.util.List;

public record JournalNote(String weekDayName, int dayOfMonth,
                          List<ActivityDetails> activityDetails,
                          String lifeActivitySummary)
{

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("[%s %d] ", weekDayName, dayOfMonth));

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
            return String.format("%d/%dh: %s\n\t\t", currentHours, goalHours, activitySummary);
        }
    }

}
