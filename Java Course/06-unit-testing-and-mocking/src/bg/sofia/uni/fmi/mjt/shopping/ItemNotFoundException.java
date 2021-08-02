package bg.sofia.uni.fmi.mjt.shopping;

public class ItemNotFoundException extends RuntimeException {
    ItemNotFoundException(String message) {
        super(message);
    }
}