package bg.sofia.uni.fmi.mjt.weather.dto;

import java.util.Objects;

public class WeatherCondition {
    private final String description;

    public WeatherCondition(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeatherCondition that = (WeatherCondition) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "описание: " + description;
    }
}
