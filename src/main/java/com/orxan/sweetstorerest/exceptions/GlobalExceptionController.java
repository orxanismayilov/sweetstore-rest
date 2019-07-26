package com.orxan.sweetstorerest.exceptions;

import com.orxan.sweetstorerest.model.ApiError;
import com.orxan.sweetstorerest.model.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(RuntimeException ex) {
        ApiError apiError=new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = InvalidProductException.class)
    public ResponseEntity<Object> handleInvalidProductException(InvalidProductException ex) {
        ResponseObject responseObject=new ResponseObject();
        responseObject.setMessage("Invalid product.");
        responseObject.setData(ex.getParam());
        return buildResponseEntity(responseObject,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidOrderProductException.class)
    public ResponseEntity<Object> handleInvalidOrderProductException(InvalidOrderProductException ex) {
        ResponseObject responseObject=new ResponseObject();
        responseObject.setMessage("Invalid OrderProduct.");
        responseObject.setData(ex.getParam());
        return buildResponseEntity(responseObject,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ResponseObject responseObject,HttpStatus status) {
        return new ResponseEntity<>(responseObject, status);
    }



    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
