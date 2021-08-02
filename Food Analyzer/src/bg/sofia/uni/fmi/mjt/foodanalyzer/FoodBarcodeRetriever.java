package bg.sofia.uni.fmi.mjt.foodanalyzer;

import java.util.Arrays;
import java.util.List;

public class FoodBarcodeRetriever extends BarcodeRetriever {

    private FoodBarcodeRetriever() {

    }

    public static String getBarcodeFromFoodRequest(String barcodeRequest) {
        List<String> words = Arrays.asList(barcodeRequest.split(" "));
        String requestArgument = words.get(1);

        if (words.size() == 2 && requestArgument.contains(".")) {
            return BarcodeRetriever.getBarcodeFromPath(requestArgument);
        }
        if (words.size() == 3 && requestArgument.contains(".")) {
            return words.get(2);
        }
        return requestArgument;

    }
}
