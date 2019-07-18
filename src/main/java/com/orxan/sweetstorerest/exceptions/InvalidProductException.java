package com.orxan.sweetstorerest.exceptions;

public class InvalidProductException extends RuntimeException {
    private Object param;

    public InvalidProductException(String message) {
        super(message);
    }

    public InvalidProductException(Object param) {
        this.param=param;
    }

    public InvalidProductException(String message, Object param) {
        super(message);
        this.param=param;
    }

    public Object getParam() {
        return param;
    }
}
