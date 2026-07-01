package com.bootcamp.smarthome.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(){
        super(new Throwable("Device not found."));
    }
}
