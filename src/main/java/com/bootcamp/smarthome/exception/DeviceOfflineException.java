package com.bootcamp.smarthome.exception;

public class DeviceOfflineException extends HomeAutomationException {
    public DeviceOfflineException(){
        super();
    }
    public DeviceOfflineException(String message){
        super(message);
    }
}
