package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

public abstract class BaseSmartDevice implements SmartDevice {
    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;

    public BaseSmartDevice(String name, double powerConsumption, LocalDateTime installationDateTime) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }
}
