package com.bootcamp.smarthome.exception;

public class HomeAutomationException extends Exception {
    public HomeAutomationException(String message, Throwable cause){
        super(message, cause);
    }

    public HomeAutomationException(String message){
        super(message);
    }

    public HomeAutomationException(){
        super();
    }
}
