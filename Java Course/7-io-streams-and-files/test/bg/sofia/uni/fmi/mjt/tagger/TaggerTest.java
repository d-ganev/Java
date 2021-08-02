package bg.sofia.uni.fmi.mjt.tagger;

import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TaggerTest {

    private static Tagger tagger;

    @Before
    public void setUpData() {
        try (var cityInfo = new FileReader("cityInfo.txt")) {
            tagger = new Tagger(cityInfo);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testTagCitiesWithRealCities() {
        try (var fileReader = new FileReader("inputWithRealCities.txt");
                var actualWriter = new FileWriter("actualResultForRealCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            String expectedResult = Files.readString(Path.of("expectedResultForRealCities.txt"));
            String actualResult = Files.readString(Path.of("actualResultForRealCities.txt"));

            assertEquals("Tag cities should work properly.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testTagCitiesWithFakeCities() {
        try (var fileReader = new FileReader("inputWithFakeCities.txt");
                var actualWriter = new FileWriter("actualResultForFakeCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            String expectedResult = Files.readString(Path.of("expectedResultForFakeCities.txt"));
            String actualResult = Files.readString(Path.of("actualResultForFakeCities.txt"));

            assertEquals("Tag cities does not recognize fake cities.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNMostTaggedCitiesWithInvalidInput() {
        final int n = -1;
        tagger.getNMostTaggedCities(n);
    }

    @Test
    public void testGetNMostTaggedCitiesWhenThereAreNoTaggedCities() {
        final int n = 1;
        Collection<String> actualResult = tagger.getNMostTaggedCities(n);
        Collection<String> expectedResult = new ArrayList<>();
        assertEquals("GetNMostTaggedCities should work properly.", expectedResult, actualResult);
    }

    @Test
    public void testGetNMostTaggedCitiesWhenThereAreMoreThanNTaggedCities() {
        try (var fileReader = new FileReader("inputWithRealCities.txt");
                var actualWriter = new FileWriter("actualResultForRealCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            final int n = 1;
            Collection<String> actualResult = tagger.getNMostTaggedCities(n);
            Collection<String> expectedResult = new ArrayList<>();
            expectedResult.add("Plovdiv");

            assertEquals("GetNMostTaggedCities should work properly.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testGetNMostTaggedCitiesWhenThereAreLessThanNTaggedCities() {
        try (var fileReader = new FileReader("inputWithRealCities.txt");
                var actualWriter = new FileWriter("actualResultForRealCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            final int n = 3;
            Collection<String> actualResult = tagger.getNMostTaggedCities(n);
            Collection<String> expectedResult = new ArrayList<>();
            expectedResult.add("Plovdiv");
            expectedResult.add("Sofia");

            assertEquals("GetNMostTaggedCities should work properly.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testGetAllTaggedCitiesWhenThereAreNoTaggedCities() {
        Collection<String> actualResult = tagger.getAllTaggedCities();
        Collection<String> expectedResult = new HashSet<>();
        assertEquals("GetAllTaggedCities should work properly.", expectedResult, actualResult);
    }

    @Test
    public void testGetAllTaggedCitiesWhenThereAreTaggedCities() {
        try (var fileReader = new FileReader("inputWithRealCities.txt");
                var actualWriter = new FileWriter("actualResultForRealCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            Collection<String> actualResult = tagger.getAllTaggedCities();
            Collection<String> expectedResult = new HashSet<>();
            expectedResult.add("Plovdiv");
            expectedResult.add("Sofia");

            assertEquals("GetNMostTaggedCities should work properly.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testGetAllTagsCountWhenThereAreNoTaggedCities() {
        long actualResult = tagger.getAllTagsCount();
        final long expectedResult = 0;
        assertEquals("GetAllTagsCount should work properly.", expectedResult, actualResult);
    }

    @Test
    public void testGetAllTagsCountWhenThereAreTaggedCities() {
        try (var fileReader = new FileReader("inputWithRealCities.txt");
                var actualWriter = new FileWriter("actualResultForRealCities.txt")) {

            tagger.tagCities(fileReader, actualWriter);
            long actualResult = tagger.getAllTagsCount();
            final long expectedResult = 4;

            assertEquals("GetNMostTaggedCities should work properly.", expectedResult, actualResult);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
