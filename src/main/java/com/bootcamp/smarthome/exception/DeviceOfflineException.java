package com.bootcamp.smarthome.exception;

public class DeviceOfflineException extends HomeAutomationException {
    public DeviceOfflineException(Throwable cause){
        super(new Throwable(cause));
    }
}
