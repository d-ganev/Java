package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends BaseSmartDevice {
    private String id;
    private static final DeviceType TYPE = DeviceType.LAMP;
    private static int numberOfInstances;

    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
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
