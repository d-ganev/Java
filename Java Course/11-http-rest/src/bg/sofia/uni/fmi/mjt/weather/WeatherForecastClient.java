package bg.sofia.uni.fmi.mjt.weather;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import bg.sofia.uni.fmi.mjt.weather.exceptions.LocationNotFoundException;
import bg.sofia.uni.fmi.mjt.weather.exceptions.WeatherForecastClientException;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherForecastClient {
    private final HttpClient client;
    private static final String API_KEY = "c074773c858e4cc293d22308593b1b08";

    public WeatherForecastClient(HttpClient weatherHttpClient) {
        client = weatherHttpClient;
    }

    public WeatherForecast getForecast(String city) throws WeatherForecastClientException {
        HttpResponse<String> response;

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(getFormattedUri(city)).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new WeatherForecastClientException("Could not retrieve weather forecast", e);
        }

        if (response.statusCode() == 404) {
            throw new LocationNotFoundException("Location " + city + " is not valid");
        }
        Gson gson = new Gson();
        return gson.fromJson(response.body(), WeatherForecast.class);
    }

    URI getFormattedUri(String city) {
        String uri;
        if (city.contains(" ")) {
            String formattedCity = city.replace(" ", "%20");
            uri = "http://api.openweathermap.org/data/2.5/weather?q="
                    + formattedCity + "&units=metric&lang=bg&appid=" + API_KEY;
        } else {
            uri = "http://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&units=metric&lang=bg&appid=" + API_KEY;
        }
        return URI.create(uri);
    }
}

