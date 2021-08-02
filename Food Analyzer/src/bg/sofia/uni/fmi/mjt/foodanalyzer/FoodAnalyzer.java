package bg.sofia.uni.fmi.mjt.foodanalyzer;

import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodDescription;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodNotFoundException;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodAnalyzer {

    private final Map<String, FoodData> foodsData;
    private final FoodDataFileWriter writer;
    private final FoodAnalyzerHttpClient httpFoodAnalyzer;

    public FoodAnalyzer() {
        HttpClient client = HttpClient.newBuilder().build();
        httpFoodAnalyzer = new FoodAnalyzerHttpClient(client);
        foodsData = new HashMap<>();
        writer = new FoodDataFileWriter();
    }

    public FoodData getFoodInformation(String foodRequest) {

        FoodData foodInDatabase = checkForFoodDataInDataBase(foodRequest);

        if (foodInDatabase != null) {
            return foodInDatabase;
        }

        if (FoodRequestValidator.isBarcodeRequest(foodRequest)) {
            throw new FoodNotFoundException("Food not found error");
        }

        FoodData foodData = httpFoodAnalyzer.getFoodData(foodRequest);
        String food = foodRequest.substring(foodRequest.indexOf(' ') + 1);
        foodsData.put(food, foodData);
        writer.write(foodData);
        return foodData;
    }

    private FoodData checkForFoodDataInDataBase(String request) {

        if (FoodRequestValidator.isBarcodeRequest(request)) {
            return retrieveFoodDataFromDatabaseByBarcode(request);
        }

        String food = request.substring(request.indexOf(' '));
        if (foodsData.containsKey(food)) {
            return foodsData.get(food);
        }

        return null;

    }

    private FoodData retrieveFoodDataFromDatabaseByBarcode(String request) {
        String barcode = FoodBarcodeRetriever.getBarcodeFromFoodRequest(request);
        List<FoodData> foodsInDataBase = new ArrayList<>(foodsData.values());
        for (FoodData currFoodData : foodsInDataBase) {
            FoodDescription[] descriptions = currFoodData.getFoods();
            if (descriptions != null) {
                for (FoodDescription currFoodDescription : descriptions) {
                    if (barcode.equals(currFoodDescription.getBarcode())) {
                        return currFoodData;
                    }
                }
            }
        }
        return null;
    }

}