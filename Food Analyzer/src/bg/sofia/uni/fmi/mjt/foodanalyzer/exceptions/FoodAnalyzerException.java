package bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions;

public class FoodAnalyzerException extends RuntimeException {

    public FoodAnalyzerException(String message, Exception e) {
        super(message, e);
    }

    public FoodAnalyzerException(String message) {
        super(message);
    }

}
