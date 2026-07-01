package com.bootcamp.smarthome.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(){
        super();
    }

    public DeviceNotFoundException(String message){
        super(message);
    }
}
