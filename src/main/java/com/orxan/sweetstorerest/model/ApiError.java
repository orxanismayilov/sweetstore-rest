package com.orxan.sweetstorerest.model;

abstract class ApiError {

}

class ApiValidationError extends ApiError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
