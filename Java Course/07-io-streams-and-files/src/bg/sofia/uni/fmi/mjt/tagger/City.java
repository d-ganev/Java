package bg.sofia.uni.fmi.mjt.tagger;

public class City {
    private final String cityName;
    private final String country;
    private int timesOfBeingTagged;

    public City(String cityName, String country) {
        this.cityName = cityName;
        this.country = country;
        timesOfBeingTagged = 0;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public int getTimesOfBeingTagged() {
        return timesOfBeingTagged;
    }

    public void increaseTimesOfBeingTagged() {
        this.timesOfBeingTagged++;
    }

    public void annulTimesOfBeingTagged() {
        this.timesOfBeingTagged = 0;
    }
}
