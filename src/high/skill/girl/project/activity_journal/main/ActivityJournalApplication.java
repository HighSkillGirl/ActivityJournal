package high.skill.girl.project.activity_journal.main;

import high.skill.girl.project.activity_journal.picocli.JournalCommands;
import picocli.CommandLine;

public class ActivityJournalApplication {
    public static void main (String... journalInfo) {
        new CommandLine(new JournalCommands()).execute(journalInfo);
    }
}
