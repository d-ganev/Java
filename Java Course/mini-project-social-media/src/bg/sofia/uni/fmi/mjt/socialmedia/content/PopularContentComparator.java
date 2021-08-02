package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.util.Comparator;

public class PopularContentComparator implements Comparator<Content> {
    @Override
    public int compare(Content obj1, Content obj2) {
        int obj1PopularityRating = obj1.getNumberOfComments() + obj1.getNumberOfLikes();
        int obj2PopularityRating = obj2.getNumberOfComments() + obj2.getNumberOfLikes();
        return obj2PopularityRating - obj1PopularityRating;       // in descending order
    }
}
