package bg.sofia.uni.fmi.mjt.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableList;


public class SpotifyExplorer {
    private final Set<SpotifyTrack> tracks;

    /*
     * Loads the dataset from the given {@code dataInput} stream.
     *
     * @param dataInput java.io.Reader input stream from which the dataset can be read
     */
    public SpotifyExplorer(Reader dataInput) {
        tracks = new HashSet<>();
        String line;
        try (BufferedReader buffInput = new BufferedReader(dataInput)) {
            buffInput.readLine();             //in case to skip the first line
            while ((line = buffInput.readLine()) != null) {
                tracks.add(SpotifyTrack.of(line));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /*
     * @return all spotify tracks from the dataset as unmodifiable collection
     * If the dataset is empty, return an empty collection
     */
    public Collection<SpotifyTrack> getAllSpotifyTracks() {
        return Collections.unmodifiableCollection(tracks);
    }

    /**
     * @return all tracks from the spotify dataset classified as explicit as unmodifiable collection
     * If the dataset is empty or contains no tracks classified as explicit, return an empty collection
     */
    public Collection<SpotifyTrack> getExplicitSpotifyTracks() {
        return tracks.stream().filter(SpotifyTrack::explicit).collect(toUnmodifiableList());
    }

    /*
     * Returns all tracks in the dataset, grouped by release year. If no tracks were released in a given year
     * it should not appear as key in the map.
     *
     * @return map with year as a key and the set of spotify tracks released this year as value.
     * If the dataset is empty, return an empty collection
     */
    public Map<Integer, Set<SpotifyTrack>> groupSpotifyTracksByYear() {
        Map<Integer, Set<SpotifyTrack>> spotifyTracksGroupedByYear = tracks.stream()
                .collect(groupingBy(SpotifyTrack::year, toSet()));

        for (Map.Entry<Integer, Set<SpotifyTrack>> currEl : spotifyTracksGroupedByYear.entrySet()) {
            if (currEl.getValue().isEmpty()) {                       // removing empty elements
                spotifyTracksGroupedByYear.remove(currEl.getKey());
            }
        }
        return spotifyTracksGroupedByYear;
    }

    /*
     * Returns the number of years between the oldest and the newest released tracks of an artist.
     * For example, if the oldest and newest tracks are released in 1996 and 1998 respectively,
     * return 3, if the oldest and newest release match, e.g. 2002-2002, return 1.
     * Note that tracks with multiple authors including the given artist should also be considered in the result.
     *
     * @param artist artist name
     * @return number of active years
     * If the dataset is empty or there are no tracks by the given artist in the dataset, return 0.
     */
    public int getArtistActiveYears(String artist) {
        int yearOfNewestTrack = tracks.stream().filter(e -> e.artists().contains(artist))
                .mapToInt(SpotifyTrack::year).max().orElse(0);

        int yearOfOldestTrack = tracks.stream().filter(e -> e.artists().contains(artist))
                .mapToInt(SpotifyTrack::year).min().orElse(0);

        if (yearOfOldestTrack == 0 || yearOfNewestTrack == 0) {
            return 0;
        }
        return yearOfNewestTrack - yearOfOldestTrack + 1;
    }

    /*
     * Returns the @n tracks with highest valence from the 80s.
     * Note that the 80s started in 1980 and lasted until 1989, inclusive.
     * Valence describes the musical positiveness conveyed by a track.
     * Tracks with high valence sound more positive (happy, cheerful, euphoric),
     * while tracks with low valence sound more negative (sad, depressed, angry).
     *
     * @param n number of tracks to return
     *          If @n exceeds the total number of tracks from the 80s, return all tracks available from this period.
     * @return unmodifiable list of tracks sorted by valence in descending order
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public List<SpotifyTrack> getTopNHighestValenceTracksFromThe80s(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The input is not valid because it is a negative number!");
        }
        final int startYearFor80s = 1980;
        final int endYearFor80s = 1989;
        return tracks.stream().filter(e -> e.year() >= startYearFor80s && e.year() <= endYearFor80s)
                .sorted(Comparator.comparingDouble(SpotifyTrack::valence).reversed())
                .limit(n).collect(toUnmodifiableList());
    }

    /*
     * Returns the most popular track from the 90s.
     * Note that the 90s started in 1990 and lasted until 1999, inclusive.
     * The value is between 0 and 100, with 100 being the most popular.
     *
     * @return the most popular track of the 90s.
     * If there more than one tracks with equal highest popularity, return any of them
     * @throws NoSuchElementException if there are no tracks from the 90s in the dataset
     */
    public SpotifyTrack getMostPopularTrackFromThe90s() {
        final int startYearFor90s = 1990;
        final int endYearFor90s = 1999;
        return tracks.stream().filter(e -> e.year() >= startYearFor90s && e.year() <= endYearFor90s)
                .reduce((e1, e2) -> {
                    if (e1.popularity() >= e2.popularity()) {
                        return e1;
                    } else {
                        return e2;
                    }
                }).orElseThrow(NoSuchElementException::new);
    }

    private double getMinutes(long milliSeconds) {
        final int converterFactor = 60000;
        return (double) milliSeconds / converterFactor;
    }

    /*
     * Returns the number of tracks longer than @minutes released before @year.
     *
     * @param minutes
     * @param year
     * @return the number of tracks longer than @minutes released before @year
     * @throws IllegalArgumentException in case @minutes or @year is a negative number
     */
    public long getNumberOfLongerTracksBeforeYear(int minutes, int year) {
        if (minutes < 0 || year < 0) {
            throw new IllegalArgumentException("The input is invalid because it is negative!");
        }
        return tracks.stream().filter(e -> e.year() < year && getMinutes(e.duration()) > minutes).count();
    }

    /*
     * Returns the loudest track released in a given year
     *
     * @param year
     * @return the loudest track released in a given year
     * @throws IllegalArgumentException in case @year is a negative number
     */
    public Optional<SpotifyTrack> getTheLoudestTrackInYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("The input is invalid because it is negative!");
        }

        return tracks.stream().filter(e -> e.year() == year)
                .reduce((e1, e2) -> {
                    if (e1.loudness() >= e2.loudness()) {
                        return e1;
                    } else {
                        return e2;
                    }
                });
    }
}