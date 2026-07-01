package com.bootcamp.smarthome.exception;

public class HomeAutomationException extends Exception {
    public HomeAutomationException(Throwable cause){
        super(new Throwable(cause));
    }
}
