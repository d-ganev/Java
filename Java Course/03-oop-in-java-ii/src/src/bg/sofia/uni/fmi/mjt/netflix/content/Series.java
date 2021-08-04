package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

non-sealed class Series extends StreamableContent {
    private Episode[] episodes;

    public Series(String name, Genre genre, PgRating rating, Episode[] episodes) {
        super(name, genre, rating);
        this.episodes = episodes;
    }

    @Override
    public int getDuration() {
        int duration = 0;
        for (Episode currEpisode : episodes) {
            duration += currEpisode.duration();
        }
        return duration;
    }
}
