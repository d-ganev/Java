package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceAlreadyRegisteredException extends RuntimeException {

    DeviceAlreadyRegisteredException(String message) {
        super(message);
    }
}
