package bg.sofia.uni.fmi.mjt.foodanalyzer;

import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodIdData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodNameData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodAnalyzerException;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodNotFoundException;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodAnalyzerHttpClient {
    private static final String API_KEY = "<API KEY>"; //take valid api key from here: https://fdc.nal.usda.gov/api-key-signup.html
    private static final Gson GSON = new Gson();
    private final HttpClient client;

    public FoodAnalyzerHttpClient(HttpClient client) {
        this.client = client;
    }

    public FoodData getFoodData(String foodRequest) {
        HttpResponse<String> response;

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(getFormattedUri(foodRequest))).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new FoodAnalyzerException("Food data retrieving error!", e);
        }

        if (response.statusCode() == 404) {
            throw new FoodNotFoundException("Food not found error!");
        }
        else if (response.statusCode() != 200) {
            throw new FoodAnalyzerException("Bad input parameters!");
        }
        return getResultFromJson(foodRequest, response);
    }

    private static String getFormattedUri(String request) {
        String[] requestWords = request.split(" ");
        String foodName = getFoodName(requestWords);

        if (requestWords[0].equals("get-food")) {
            return "https://api.nal.usda.gov/fdc/v1/foods/search?query="
                    + foodName + "&requireAllWords=true&api_key=" + API_KEY;
        } else if (requestWords[0].equals("get-food-report")) {
            String foodId = requestWords[1];
            return "https://api.nal.usda.gov/fdc/v1/food/" + foodId + "?api_key=" + API_KEY;
        } else {
            return "";
        }
    }

    private static String getFoodName(String[] requestWords) {
        StringBuilder foodName = new StringBuilder(requestWords[1]);
        if (requestWords.length > 2) {
            for (int i = 2; i < requestWords.length; i++) {
                foodName.append("%20").append(requestWords[i]);
            }
        }
        return foodName.toString();
    }

    private static FoodData getResultFromJson(String request, HttpResponse<String> response) {
        if (request.substring(0, request.indexOf(' ')).equals("get-food-report")) {
            return getFoodDataById(response);
        } else {
            return getFoodDataByName(request, response);
        }
    }

    private static FoodData getFoodDataById(HttpResponse<String> response) {
        return GSON.fromJson(response.body(), FoodIdData.class);
    }

    private static FoodData getFoodDataByName(String request, HttpResponse<String> response) {
        FoodNameData foodData = GSON.fromJson(response.body(), FoodNameData.class);
        if (Integer.parseInt(foodData.getTotalHits()) == 0) {
            throw new FoodNotFoundException("Food not found error!");
        }
        foodData.setFoodName(request.substring(request.indexOf(' ') + 1));
        return foodData;
    }
}
