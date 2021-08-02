package bg.sofia.uni.fmi.mjt.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class WeatherForecast {
    @SerializedName("weather")
    private final WeatherCondition[] weatherConditions;
    @SerializedName("main")
    private final WeatherData weatherData;
    private transient String city;

    public WeatherForecast(WeatherCondition[] weatherConditions, WeatherData weatherData) {
        this.weatherConditions = weatherConditions;
        this.weatherData = weatherData;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeatherForecast that = (WeatherForecast) o;
        return Arrays.equals(weatherConditions, that.weatherConditions) &&
                Objects.equals(weatherData, that.weatherData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(weatherData);
        result = 31 * result + Arrays.hashCode(weatherConditions);
        return result;
    }

    @Override
    public String toString() {
        return "Прогноза за времето в " + city + " e: " + Arrays.toString(weatherConditions) + ", " + weatherData;
    }
}
