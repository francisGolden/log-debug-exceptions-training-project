package com.bootcamp.smarthome.controller;

import com.bootcamp.smarthome.device.Device;
import com.bootcamp.smarthome.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Central hub that manages all registered smart devices.
 *
 * Devices are stored in a fixed-size array (maximum {@value #MAX_DEVICES}).
 * The controller routes commands to devices by their ID.
 */
public class HomeController {

    public static final int MAX_DEVICES = 8;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final Device[] devices = new Device[MAX_DEVICES];
    private int deviceCount = 0;

    // -------------------------------------------------------------------------
    // Device registration
    // -------------------------------------------------------------------------

    /**
     * Registers a new device with the controller.
     * The controller accepts at most {@value #MAX_DEVICES} devices.
     *
     * @param device the device to register
     * @throws IllegalStateException if the device limit has been reached
     */
    public void addDevice(Device device) {
        if (deviceCount >= MAX_DEVICES) {
            throw new IllegalStateException(
                    "Cannot add device '" + device.getDeviceId() +
                    "': controller is at maximum capacity (" + MAX_DEVICES + ").");
        }
        devices[deviceCount] = device;
        deviceCount++;
        logger.info("Device registered: " + device);
    }

    // -------------------------------------------------------------------------
    // Device lookup
    // -------------------------------------------------------------------------

    /**
     * Finds a registered device by its ID.
     *
     * Returns {@code null} when no matching device is found.
     */
    public Device findDevice(String deviceId) {
        for (int i = 0; i <= deviceCount; i++) {
            if (devices[i] != null && devices[i].getDeviceId().equals(deviceId)) {
                return devices[i];
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Command routing
    // -------------------------------------------------------------------------

    /**
     * Parses {@code fullCommand}, resolves the target device, and delegates
     * execution to {@link Device#executeCommand(String)}.
     *
     * Full command format: {@code "DEVICE_ID ACTION [VALUE]"}
     * Example: {@code "LIGHT_01 SET_BRIGHTNESS 75"}
     *
     * @param fullCommand the full command string
     */
    public void sendCommand(String fullCommand) throws HomeAutomationException {

        String deviceId = CommandParser.extractDeviceId(fullCommand);
        String command  = CommandParser.extractCommand(fullCommand);
        logger.debug("Received command [{}]", fullCommand);

        try {
            Device device = findDevice(deviceId);
            if (device == null) {
                logger.warn("Received command [{}] WARNING: Device [{}] is not found — command skipped.", command, deviceId);
                return;
//                throw new DeviceNotFoundException("Device not found: " + deviceId);
            }
            if (!device.isOnline()) {
                logger.warn("Received command [{}] WARNING: Device [{}] is offline — command skipped.", command, deviceId);
                return;
//               throw new DeviceOfflineException("WARNING: Device '" + deviceId + "' is offline — command skipped.");
            }
            device.executeCommand(command);

            logger.info("Command [{}] executed successfully.", command);
        } catch (HomeAutomationException e) {
            logger.error("Command [{}] failed for device [{}]'", command, deviceId);
            throw new HomeAutomationException("Command '" + fullCommand + "' failed for device '" + deviceId + "'", e);
        } finally {
            logger.info("Command processing ended for device [{}]", deviceId);
        }

    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /** Prints the status of every registered device. */
    public void printAllDevices() {
        logger.info("=== Registered Devices ({}/{}) ===", deviceCount, MAX_DEVICES);
        for (int i = 0; i < deviceCount; i++) {
            logger.info("[{}]", devices[i]);
        }
    }

    public int getDeviceCount() {
        return deviceCount;
    }
}
