package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceNotFoundException extends RuntimeException {

    DeviceNotFoundException(String message) {
        super(message);
    }
}
