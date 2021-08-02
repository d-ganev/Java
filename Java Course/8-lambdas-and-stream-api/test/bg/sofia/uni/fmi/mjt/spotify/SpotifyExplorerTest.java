package bg.sofia.uni.fmi.mjt.spotify;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


import static org.junit.Assert.assertEquals;

public class SpotifyExplorerTest {

    private static final String TEST_TRACK1_CONTENT = "id1,['artist1'; 'artist2']" +
            ",TEST_TRACK1,1990,90,831667,80.954,0,0.0594,0.982,0.279,0.211,0.665,0.0366,0";

    private static final String TEST_TRACK2_CONTENT = "id2,['artist']" +
            ",TEST_TRACK2,1980,0,279171,120,-8.124,0.500,0.447,0.708,0.544,0.0733,0.0316,1";

    private static final String TEST_TRACK3_CONTENT = "id3,['artist']" +
            ",TEST_TRACK3,1981,100,279171,120,-8.124,0.406,0.447,0.708,0.544,0.0733,0.0316,1";

    private static final String TEST_TRACK4_CONTENT = "id4,['artist1'; 'artist2']" +
            ",TEST_TRACK4,1990,4,831667,80.954,-20.096,0.0594,0.982,0.279,0.211,0.665,0.0366,0";

    private static final SpotifyTrack TEST_TRACK1 = SpotifyTrack.of(TEST_TRACK1_CONTENT);
    private static final SpotifyTrack TEST_TRACK2 = SpotifyTrack.of(TEST_TRACK2_CONTENT);
    private static final SpotifyTrack TEST_TRACK3 = SpotifyTrack.of(TEST_TRACK3_CONTENT);
    private static final SpotifyTrack TEST_TRACK4 = SpotifyTrack.of(TEST_TRACK4_CONTENT);

    private SpotifyExplorer spotifyExplorer;

    @Test
    public void testGetAllSpotifyTracksWhenThereAreTracks() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getAllSpotifyTracks());
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();
        expectedResult.add(TEST_TRACK1);
        expectedResult.add(TEST_TRACK2);
        expectedResult.add(TEST_TRACK3);
        expectedResult.add(TEST_TRACK4);
        assertEquals("Get all spotify tracks does not work properly, when there are tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetAllSpotifyTracksWhenThereAreNoTracks() {
        try (var spotifyData = new FileReader("emptyFile.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getAllSpotifyTracks());
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();
        assertEquals("Get all spotify tracks does not work properly, when there are no tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetExplicitSpotifyTracksWhenThereAreExplicitTracks() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getExplicitSpotifyTracks());
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();
        expectedResult.add(TEST_TRACK2);
        expectedResult.add(TEST_TRACK3);

        assertEquals("Get explicit spotify tracks does not work properly, when there are tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetExplicitSpotifyTracksWhenThereAreNoExplicitTracks() {
        try (var spotifyData = new FileReader("emptyFile.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getExplicitSpotifyTracks());
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();

        assertEquals("Get explicit spotify tracks does not work properly, when there are no tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGroupSpotifyTracksByYear() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int year1 = 1990;
        final Set<SpotifyTrack> trackSet1 = new LinkedHashSet<>();
        trackSet1.add(TEST_TRACK1);
        trackSet1.add(TEST_TRACK4);

        final int year2 = 1980;
        final Set<SpotifyTrack> trackSet2 = new LinkedHashSet<>();
        trackSet2.add(TEST_TRACK2);

        final int year3 = 1981;
        final Set<SpotifyTrack> trackSet3 = new LinkedHashSet<>();
        trackSet3.add(TEST_TRACK3);

        Map<Integer, Set<SpotifyTrack>> actualResult = spotifyExplorer.groupSpotifyTracksByYear();
        Map<Integer, Set<SpotifyTrack>> expectedResult = new HashMap<>();
        expectedResult.put(year1, trackSet1);
        expectedResult.put(year2, trackSet2);
        expectedResult.put(year3, trackSet3);

        assertEquals("Group spotify tracks by year does not work properly, when there are tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGroupSpotifyTracksByYearWhenThereAreNoTracks() {
        try (var spotifyData = new FileReader("emptyFile.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Map<Integer, Set<SpotifyTrack>> actualResult = spotifyExplorer.groupSpotifyTracksByYear();
        Map<Integer, Set<SpotifyTrack>> expectedResult = new HashMap<>();

        assertEquals("Group spotify tracks by year does not work properly, when there are no tracks!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetArtistActiveYears() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int actualResult = spotifyExplorer.getArtistActiveYears("artist");
        final int expectedResult = 2;

        assertEquals("Get artist active years does not work properly, when there are expected artist!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetArtistActiveYearsWhenThereAreNoSuchArtist() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int actualResult = spotifyExplorer.getArtistActiveYears("fakeArtist");
        final int expectedResult = 0;

        assertEquals("Get artist active years does not work properly, when the expected artist is absent!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetArtistActiveYearsWhenTheFileIsEmpty() {
        try (var spotifyData = new FileReader("emptyFile.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int actualResult = spotifyExplorer.getArtistActiveYears("artist");
        final int expectedResult = 0;

        assertEquals("Get artist active years does not work properly, when the file is empty!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80s() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int n = 2;
        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getTopNHighestValenceTracksFromThe80s(n));
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();
        expectedResult.add(TEST_TRACK2);
        expectedResult.add(TEST_TRACK3);

        assertEquals("Get top N highest Valence tracks from the 80s does not work properly!"
                , expectedResult, actualResult);
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80sWhenNisGreaterThanAvailableContentNumber() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int n = 10;
        Set<SpotifyTrack> actualResult = new LinkedHashSet<>(spotifyExplorer.getTopNHighestValenceTracksFromThe80s(n));
        Set<SpotifyTrack> expectedResult = new LinkedHashSet<>();
        expectedResult.add(TEST_TRACK2);
        expectedResult.add(TEST_TRACK3);

        assertEquals("Get top N highest Valence tracks from the 80s does not work properly!"
                , expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTopNHighestValenceTracksFromThe80sWithIllegalArgument() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int n = -1;
        spotifyExplorer.getTopNHighestValenceTracksFromThe80s(n);
    }

    @Test
    public void testGetMostPopularTrackFromThe90s() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        SpotifyTrack actualResult = spotifyExplorer.getMostPopularTrackFromThe90s();

        assertEquals("Get most popular track from the 90s does not work properly, when there are tracks from the 90s!"
                , TEST_TRACK1, actualResult);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMostPopularTrackFromThe90sWhenThereAreNoTracksFromThe90s() {
        try (var spotifyData = new FileReader("emptyFile.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        spotifyExplorer.getMostPopularTrackFromThe90s();
    }

    @Test
    public void testGetNumberOfLongerTracksBeforeYear() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int year = 2020;
        final int minutes = 5;
        long actualResult = spotifyExplorer.getNumberOfLongerTracksBeforeYear(minutes, year);
        final long expectedResult = 2;

        assertEquals("Get number of longer tracks before year does not work properly!"
                , expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNumberOfLongerTracksBeforeYearWhenTheInputIsInvalid() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int year = -1;
        final int minutes = 5;
        spotifyExplorer.getNumberOfLongerTracksBeforeYear(minutes, year);
    }

    @Test
    public void testGetTheLoudestTrackInYear() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int year = 1990;
        Optional<SpotifyTrack> actualResult = spotifyExplorer.getTheLoudestTrackInYear(year);
        Optional<SpotifyTrack> expectedResult = Optional.of(TEST_TRACK1);

        assertEquals("Get the loudest track in year does not work properly!"
                , expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTheLoudestTrackInYearWhenTheInputIsInvalid() {
        try (var spotifyData = new FileReader("test-spotifyTracks.csv")) {
            spotifyExplorer = new SpotifyExplorer(spotifyData);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        final int year = -1;
        spotifyExplorer.getTheLoudestTrackInYear(year);
    }
}
