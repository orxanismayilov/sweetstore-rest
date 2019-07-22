package com.orxan.sweetstorerest.exceptions;

public class InvalidOrderProductException extends RuntimeException {
    private Object param;

    public InvalidOrderProductException(String message) {
        super(message);
    }

    public InvalidOrderProductException(Object param) {
        this.param=param;
    }

    public InvalidOrderProductException(String message, Object param) {
        super(message);
        this.param=param;
    }

    public Object getParam() {
        return param;
    }
}
