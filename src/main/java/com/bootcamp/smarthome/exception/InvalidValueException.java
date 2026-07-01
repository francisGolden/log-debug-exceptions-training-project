package com.bootcamp.smarthome.exception;

public class InvalidValueException extends HomeAutomationException {
    private String field;
    private Object value;
    private String constraint;

    public InvalidValueException(String field, Object value, String constraint){
        this.field = field;
        this.value = value;
        this.constraint = constraint;
    }
}
