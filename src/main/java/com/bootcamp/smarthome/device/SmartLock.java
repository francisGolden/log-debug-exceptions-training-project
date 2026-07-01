package com.bootcamp.smarthome.device;

import com.bootcamp.smarthome.controller.HomeController;
import com.bootcamp.smarthome.exception.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PIN-protected smart door lock.
 *
 * The lock can be locked or unlocked via a 4-digit PIN.
 * Every failed unlock attempt is treated as a security event.
 */
public class SmartLock extends Device {

    private boolean isLocked;
    private final String storedPin;
    private static final Logger logger = LoggerFactory.getLogger(SmartLock.class);

    public SmartLock(String deviceId, String name, boolean isOnline, String pin) {
        super(deviceId, name, isOnline);
        this.isLocked = true;
        this.storedPin = pin;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Validates the supplied PIN against the stored PIN.
     */
    public void validatePin(String pin) throws InvalidCommandException {
        if (pin == null  || pin.isBlank()) {
            logger.error("SECURITY ALERT: PIN cannot be empty for [{}].", getName());
            throw new InvalidCommandException("Pin cannot be empty.");
        } else if (!pin.equals(storedPin)) {
            logger.error("SECURITY ALERT: Incorrect PIN entered for [{}].", getName());
            throw new InvalidCommandException("Incorrect PIN entered for " + getName());
        }
        isLocked = false;
        logger.info("[{}] unlocked successfully.", getName());
    }

    public void lock() {
        isLocked = true;
        logger.info("[{}] locked.", getName());
    }

    @Override
    public void executeCommand(String command) throws InvalidCommandException {
        if (command.startsWith("UNLOCK")) {
            String[] parts = command.split(" ");
            String pin = (parts.length > 1) ? parts[1] : null;
            validatePin(pin);
        } else if (command.equals("LOCK")) {
            lock();
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            logger.warn("Unknown command for SmartLock [{}]: [{}]", getName(), command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | locked=%b", isLocked);
    }
}
