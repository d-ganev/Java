package bg.sofia.uni.fmi.mjt.socialmedia.user;

import bg.sofia.uni.fmi.mjt.socialmedia.ActivityLog;
import bg.sofia.uni.fmi.mjt.socialmedia.content.ExpirableContent;

import java.util.HashSet;

public class User {
    private final String username;
    private final HashSet<ExpirableContent> ownSocialContent;
    private final ActivityLog activityLog;
    private int numberOfMentions;

    public User(String username) {
        this.username = username;
        ownSocialContent = new HashSet<>();
        activityLog = new ActivityLog();
        numberOfMentions = 0;
    }

    public ActivityLog getActivityLog() {
        return activityLog;
    }

    public String getUsername() {
        return username;
    }

    public HashSet<ExpirableContent> getOwnSocialContent() {
        return ownSocialContent;
    }

    public int getNumberOfMentions() {
        return numberOfMentions;
    }

    public void increaseMentions() {
        numberOfMentions++;
    }

    public void addContent(ExpirableContent content) {
        ownSocialContent.add(content);
    }
}
