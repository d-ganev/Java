package bg.sofia.uni.fmi.mjt.socialmedia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class ActivityLog {
    private final TreeMap<LocalDateTime, String> activities;

    public ActivityLog() {
        activities = new TreeMap<>();
    }

    public TreeMap<LocalDateTime, String> getActivities() {
        return activities;
    }

    private String formatTime(LocalDateTime activityTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yy");
        return activityTime.format(formatter);
    }


    public void addPostPublishingActivity(LocalDateTime activityTime, String postId) {
        String formattedTime = formatTime(activityTime);
        String activity = formattedTime + ": Created a post with id " + postId;
        activities.put(activityTime, activity);
    }

    public void addStoryPublishingActivity(LocalDateTime activityTime, String storyId) {
        String formattedTime = formatTime(activityTime);
        String activity = formattedTime + ": Created a story with id " + storyId;
        activities.put(activityTime, activity);
    }

    public void addContentLikingActivity(LocalDateTime activityTime, String contentId) {
        String formattedTime = formatTime(activityTime);
        String activity = formattedTime + ": Liked a content with id " + contentId;
        activities.put(activityTime, activity);
    }

    public void addContentCommentingActivity(LocalDateTime activityTime, String commentText, String postId) {
        String formattedTime = formatTime(activityTime);
        String activity = formattedTime + ": Commented \"" + commentText + "\" on a content with id " + postId;
        activities.put(activityTime, activity);
    }
}
