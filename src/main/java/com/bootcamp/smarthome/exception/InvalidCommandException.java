package com.bootcamp.smarthome.exception;

public class InvalidCommandException extends HomeAutomationException {
    public InvalidCommandException(){
        super();
    }

    public InvalidCommandException(String message){
        super(message);
    }
}
