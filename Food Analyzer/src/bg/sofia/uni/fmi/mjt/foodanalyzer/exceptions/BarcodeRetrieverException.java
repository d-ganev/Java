package bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions;

public class BarcodeRetrieverException extends RuntimeException {

    public BarcodeRetrieverException(String message, Exception e) {
        super(message, e);
    }

}
