package bg.sofia.uni.fmi.mjt.foodanalyzer;

import java.util.Arrays;
import java.util.List;

public class FoodRequestValidator {

    private static final List<String> commands = Arrays.asList
            ("get-food", "get-food-report", "get-food-by-barcode");

    private FoodRequestValidator() {
    }

    public static boolean isRequestValid(String request) {
        List<String> words = Arrays.asList(request.split(" "));
        if (words.size() < 2
                || !commands.contains(words.get(0))
                || words.contains(" ")
                || (isBarcodeRequest(request) && words.size() > 3)) {
            return false;
        }
        return true;
    }

    public static boolean isBarcodeRequest(String request) {
        return Arrays.asList(request.split(" ")).get(0).equals("get-food-by-barcode");
    }

}
