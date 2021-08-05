package jetbrains.com;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartCamera;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartLamp;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartTrafficLight;
import bg.sofia.uni.fmi.mjt.smartcity.hub.SmartCityHub;

import java.time.LocalDateTime;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        //creating an instance of Smart city hub
        LocalDateTime date1 = LocalDateTime.now();
        SmartDevice sd1 = new SmartCamera("name1", 2.5, date1);
        SmartDevice sd2 = new SmartCamera("name2", 2.5, date1);
        SmartDevice sd3 = new SmartCamera("name3", 2.5, date1);
        SmartDevice sd4 = new SmartCamera("name4", 2.5, date1);

        SmartCityHub smartCityHub = new SmartCityHub();
        smartCityHub.register(sd1);
        smartCityHub.register(sd3);
        smartCityHub.register(sd2);
        smartCityHub.register(sd4);

        //showing some functionality of the instance
        Collection<SmartDevice> firstNel = smartCityHub.getFirstNDevicesByRegistration(3);
        for (SmartDevice curr : firstNel) {
            System.out.println(curr.getId());
        }
    }
}
