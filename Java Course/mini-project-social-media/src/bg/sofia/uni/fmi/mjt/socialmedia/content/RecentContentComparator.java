package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.util.Comparator;

public class RecentContentComparator implements Comparator<ExpirableContent> {

    @Override
    public int compare(ExpirableContent obj1, ExpirableContent obj2) {
        if (obj1.dateOfPublishing().isBefore(obj2.dateOfPublishing())) {   // in descending order
            return 1;
        }
        if (obj1.dateOfPublishing().isEqual(obj2.dateOfPublishing())) {
            return 0;
        }
        return -1;
    }
}
