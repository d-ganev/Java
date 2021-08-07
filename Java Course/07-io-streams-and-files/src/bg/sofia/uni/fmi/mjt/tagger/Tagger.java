package bg.sofia.uni.fmi.mjt.tagger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Tagger {
    private final ArrayList<City> cities;
    private boolean hasFunctionTagCitiesBeenInvoked;

    public Tagger(Reader citiesReader) {
        cities = new ArrayList<>();
        hasFunctionTagCitiesBeenInvoked = false;
        extractCitiesInfoFromReader(citiesReader);
    }

    public void tagCities(Reader text, Writer output) {
        hasFunctionTagCitiesBeenInvoked = true;
        annulTimesOfBeingTaggedForAllCities();
        try (BufferedReader bufferedCitiesReader = new BufferedReader(text)) {
            String line;
            Set<String> namesOfCities = getNamesOfCities();
            line = bufferedCitiesReader.readLine();
            while (line != null) {
                output.write(getFormattedLine(line, namesOfCities));
                output.flush();
                line = bufferedCitiesReader.readLine();
                if (line != null) {                       // in order not to put new line when last line
                    output.write(System.lineSeparator());
                    output.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Collection<String> getNMostTaggedCities(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The input is invalid");
        }
        Set<City> mostTaggedCities = new TreeSet<>(new TaggedCitiesComparator());
        if (!hasFunctionTagCitiesBeenInvoked) {
            return new ArrayList<>();

        }

        for (City currCity : cities) {
            if (currCity.getTimesOfBeingTagged() > 0) {
                mostTaggedCities.add(currCity);
            }
        }

        ArrayList<String> namesOfMostTaggedCities = new ArrayList<>();
        for (City currCity : mostTaggedCities) {
            if (namesOfMostTaggedCities.size() == n) {
                return namesOfMostTaggedCities;
            }
            namesOfMostTaggedCities.add(currCity.getCityName());
        }
        return namesOfMostTaggedCities;
    }

    public Collection<String> getAllTaggedCities() {
        if (!hasFunctionTagCitiesBeenInvoked) {
            return new HashSet<>();
        }
        Set<String> taggedCities = new HashSet<>();
        for (City currCity : cities) {
            if (currCity.getTimesOfBeingTagged() > 0) {
                taggedCities.add(currCity.getCityName());
            }
        }
        return taggedCities;
    }

    public long getAllTagsCount() {
        if (!hasFunctionTagCitiesBeenInvoked) {
            return 0;
        }
        long totalNumberOfTags = 0;
        for (City currCity : cities) {
            totalNumberOfTags += currCity.getTimesOfBeingTagged();
        }
        return totalNumberOfTags;
    }

    private void extractCitiesInfoFromReader(Reader citiesReader) {
        try (BufferedReader bufferedCitiesReader = new BufferedReader(citiesReader)) {
            String line;
            while ((line = bufferedCitiesReader.readLine()) != null) {
                int endIndexOfCity = line.indexOf(',');
                String currentCity = line.substring(0, endIndexOfCity);
                String currentCountry = line.substring(endIndexOfCity + 1);
                cities.add(new City(currentCity, currentCountry));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private boolean shouldBeTagged(String input, String city, int startIndex) {
        int indexOfCharAfterSearchedContent = input.indexOf(city, startIndex) + city.length();
        boolean isSearchedWordStartWord = (input.indexOf(city, startIndex) == 0);
        boolean isSearchedWordFinalWord = (indexOfCharAfterSearchedContent >= input.length());

        if (!isSearchedWordStartWord
                && Character.isLetter(input.charAt(input.indexOf(city, startIndex) - 1))) {
            return false;
        }
        return isSearchedWordFinalWord
                || !Character.isLetter(input.charAt(indexOfCharAfterSearchedContent));
    }

    private int getIndexOfCaseIntensively(String input, String searchedContent, int startIndex) {
        String loweredCasedInput = input.toLowerCase();
        String lowerCasedSearchedContent = searchedContent.toLowerCase();

        if (loweredCasedInput.substring(startIndex).contains(lowerCasedSearchedContent)
                && shouldBeTagged(loweredCasedInput, lowerCasedSearchedContent, startIndex)) {
            return loweredCasedInput.indexOf(lowerCasedSearchedContent, startIndex);
        }

        return -1;
    }

    private City getCityByName(String searchedCityName) {
        for (City currCity : cities) {
            if (currCity.getCityName().equals(searchedCityName)) {
                return currCity;
            }
        }
        return null;
    }

    private Set<String> getNamesOfCities() {
        Set<String> namesOfCities = new HashSet<>();
        for (City currCity : cities) {
            namesOfCities.add(currCity.getCityName());
        }
        return namesOfCities;
    }

    private String getFormattedNameOfCity(String city) {
        if (city.equalsIgnoreCase("eMbalenhle")) {
            return "eMbalenhle";
        }
        if (city.equalsIgnoreCase("eSikhawini")) {
            return "eSikhawini";
        }
        return city.substring(0, 1).toUpperCase() + city.toLowerCase().substring(1);
    }

    private String getTaggedCity(String cityToBeTagged) {
        String formattedCityToBeTagged = getFormattedNameOfCity(cityToBeTagged);
        String countryOfCurrCity = getCityByName(formattedCityToBeTagged).getCountry();
        return "<city country=\"" + countryOfCurrCity + "\">" + cityToBeTagged + "</city>";
    }

    private int getEndIndexOfWord(String input, int startIndex) {
        int endIndex = startIndex;
        while (endIndex < input.length() && Character.isLetter(input.charAt(endIndex))) {
            endIndex++;
        }
        return endIndex;
    }

    private String replaceBetweenIndexes(String input, int startIndex, int endIndex, String regex) {
        return input.substring(0, startIndex) + regex + input.substring(endIndex);
    }

    private void annulTimesOfBeingTaggedForAllCities() {
        for (City currCity : cities) {
            currCity.annulTimesOfBeingTagged();
        }
    }

    private String getFormattedLine(String line, Set<String> citiesInDataBase) {
        String formattedLine = line;
        for (String currCity : citiesInDataBase) {
            int beginningOfCurrCity = getIndexOfCaseIntensively(formattedLine, currCity, 0);
            while (beginningOfCurrCity != -1) {
                int endOfCurrCity = getEndIndexOfWord(formattedLine, beginningOfCurrCity);
                String cityToBeTagged = formattedLine.substring(beginningOfCurrCity, endOfCurrCity);
                String taggedCity = getTaggedCity(cityToBeTagged);
                formattedLine = replaceBetweenIndexes(formattedLine, beginningOfCurrCity, endOfCurrCity, taggedCity);
                getCityByName(currCity).increaseTimesOfBeingTagged();      // tagging of a city
                int startIndexForSearching = beginningOfCurrCity + taggedCity.length();
                beginningOfCurrCity = getIndexOfCaseIntensively(formattedLine, currCity, startIndexForSearching);
            }
        }
        return formattedLine;
    }
}