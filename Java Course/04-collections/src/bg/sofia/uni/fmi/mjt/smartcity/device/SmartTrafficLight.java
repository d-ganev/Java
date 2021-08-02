package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartTrafficLight extends BaseSmartDevice {
    private String id;
    private static final DeviceType TYPE = DeviceType.TRAFFIC_LIGHT;
    private static int numberOfInstances;

    public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        numberOfInstances++;
    }

    {
        id = TYPE.getShortName() + '-' + super.getName() + '-' + Integer.toString(numberOfInstances);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DeviceType getType() {
        return TYPE;
    }
}
