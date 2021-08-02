package bg.sofia.uni.fmi.mjt.socialmedia.content;

public class Post extends BaseContent {
    private static final int POST_DURATION = 24 * 30;

    public Post(String id) {
        super(id);
    }

    public static int getPostDuration() {
        return POST_DURATION;
    }

}
