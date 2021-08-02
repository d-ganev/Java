package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevicePowerConsumptionComparator;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class SmartCityHub {
    private LinkedHashSet<SmartDevice> smartDevices;

    public SmartCityHub() {
        smartDevices = new LinkedHashSet<>();
    }

    private void checkForCorrectSmartDeviceInput(SmartDevice device) {
        if (device == null) {
            throw new IllegalArgumentException("The input is null");
        }
    }

    private void checkForCorrectIdInput(String id) {
        if (id == null) {
            throw new IllegalArgumentException("The input is null");
        }
    }

    private void checkForCorrectDeviceTypeInput(DeviceType type) {
        if (type == null) {
            throw new IllegalArgumentException("The input is null");
        }
    }

    private void checkForDeviceRegistration(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if (smartDevices.contains(device)) {
            throw new DeviceAlreadyRegisteredException("The device is already registered!");
        }
    }

    private void checkForLackOfDeviceRegistrationById(String id) throws DeviceNotFoundException {
        for (SmartDevice currDevice : smartDevices) {
            String currentDeviceId = currDevice.getId();
            if (currentDeviceId.equals(id)) {
                return;
            }
        }
        throw new DeviceNotFoundException("The device with this ID is not registered!");
    }


    private void checkForLackOfDeviceRegistration(SmartDevice device) throws DeviceNotFoundException {
        if (!smartDevices.contains(device)) {
            throw new DeviceNotFoundException("The device is not registered in order to be unregistered!");
        }
    }

    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        try {
            checkForCorrectSmartDeviceInput(device);
            checkForDeviceRegistration(device);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null", e);
        } catch (DeviceAlreadyRegisteredException e) {
            throw new DeviceAlreadyRegisteredException("The device is already registered!");
        }

        smartDevices.add(device);
    }

    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        try {
            checkForCorrectSmartDeviceInput(device);
            checkForLackOfDeviceRegistration(device);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null", e);
        } catch (DeviceNotFoundException e) {
            throw new DeviceNotFoundException("The device is not registered in order to be unregistered!");
        }

        smartDevices.remove(device);
    }

    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        try {
            checkForCorrectIdInput(id);
            checkForLackOfDeviceRegistrationById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null", e);
        } catch (DeviceNotFoundException e) {
            throw new DeviceNotFoundException("The device is not registered in order to be unregistered!");
        }

        for (SmartDevice currDevice : smartDevices) {
            String currentDeviceId = currDevice.getId();
            if (currentDeviceId.equals(id)) {
                return currDevice;
            }
        }
        return null;
    }

    public int getDeviceQuantityPerType(DeviceType type) {
        try {
            checkForCorrectDeviceTypeInput(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input is null", e);
        }
        int totalQuantity = 0;
        for (SmartDevice currDevice : smartDevices) {
            DeviceType currentDeviceType = currDevice.getType();
            if (currentDeviceType == type) {
                totalQuantity++;
            }
        }
        return totalQuantity;
    }

    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input is negative integer!");
        }

        if (n >= smartDevices.size()) {
            return smartDevices;
        }

        Collection<SmartDevice> firstNDevicesByRegistration = new LinkedHashSet<>();
        int limit = n;
        for (Iterator<SmartDevice> it = smartDevices.iterator();
             it.hasNext() || limit == 0; ) {
            firstNDevicesByRegistration.add(it.next());
            limit--;
        }
        return firstNDevicesByRegistration;
    }

    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input is negative integer!");
        }
        Collection<SmartDevice> sortedSmartDevices =
                new TreeSet<>(new SmartDevicePowerConsumptionComparator());
        sortedSmartDevices.addAll(smartDevices);
        Collection<String> sortedDevicesIds = new LinkedHashSet<>();
        int limit = n;
        for (SmartDevice currDevice : sortedSmartDevices) {
            if (limit == 0) {
                break;
            }
            String currDeviceId = currDevice.getId();
            sortedDevicesIds.add(currDeviceId);
            limit--;
        }
        return sortedDevicesIds;
    }

}

