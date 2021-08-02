package bg.sofia.uni.fmi.mjt.socialmedia.content;

public class Story extends BaseContent {
    private static final int STORY_DURATION = 24;

    public Story(String id) {
        super(id);
    }

    public static int getStoryDuration() {
        return STORY_DURATION;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
