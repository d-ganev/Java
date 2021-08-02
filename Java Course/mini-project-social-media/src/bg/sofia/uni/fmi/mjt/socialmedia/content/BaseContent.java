package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseContent implements Content {
    private int numberOfLikes;
    private int numberOfComments;
    private final Set<String> tags = new HashSet<>();
    private final Set<String> mentions = new HashSet<>();
    private final String id;

    public BaseContent(String id) {
        this.id = id;
    }

    @Override
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    @Override
    public int getNumberOfComments() {
        return numberOfComments;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<String> getTags() {
        return tags;
    }

    @Override
    public Collection<String> getMentions() {
        return mentions;
    }

    public void increaseComments() {
        numberOfComments++;
    }

    public void increaseLikes() {
        numberOfLikes++;
    }
}
