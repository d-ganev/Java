package bg.sofia.uni.fmi.mjt.tagger;

import java.util.Comparator;

public class TaggedCitiesComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return Integer.compare(o2.getTimesOfBeingTagged(), o1.getTimesOfBeingTagged());
    }
}
