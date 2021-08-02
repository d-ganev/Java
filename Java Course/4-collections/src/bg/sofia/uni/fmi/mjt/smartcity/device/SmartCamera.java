package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartCamera extends BaseSmartDevice {
    private String id;
    private static final DeviceType TYPE = DeviceType.CAMERA;
    private static int numberOfInstances;

    public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime) {
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
