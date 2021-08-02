package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public sealed abstract class StreamableContent implements Streamable  permits Movie, Series{
    private String name;
    private Genre genre;
    private PgRating rating;
    private int numberOfWatches;

    StreamableContent(String name, Genre genre, PgRating rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        numberOfWatches = 0;
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public int getNumberOfWatches() {
        return numberOfWatches;
    }

    public void increaseWatches() {
        numberOfWatches++;
    }

    @Override
    public PgRating getRating() {
        return this.rating;
    }
}
