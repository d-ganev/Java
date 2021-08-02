package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

public class SmartDevicePowerConsumptionComparator implements Comparator<SmartDevice> {

    @Override
    public int compare(SmartDevice object1, SmartDevice object2) {
        LocalDateTime installationTimeOfObj1 = object1.getInstallationDateTime();
        int elapsedHoursOfObj1 = (int) Duration.between(installationTimeOfObj1, LocalDateTime.now()).toHours();
        double obj1PowerConsumption = elapsedHoursOfObj1 * object1.getPowerConsumption();

        LocalDateTime installationTimeOfObj2 = object2.getInstallationDateTime();
        int elapsedHoursOfObj2 = (int) Duration.between(installationTimeOfObj2, LocalDateTime.now()).toHours();
        double obj2PowerConsumption = elapsedHoursOfObj2 * object1.getPowerConsumption();

        return Double.compare(obj1PowerConsumption, obj2PowerConsumption);
    }
}
