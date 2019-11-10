package com.orxan.sweetstorerest.exceptions;

import com.orxan.sweetstorerest.model.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(RuntimeException ex) {
        ResponseObject<String> responseObject = new ResponseObject<>();
        responseObject.setMessage("error");
        responseObject.setData(ex.getMessage());
        return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidProductException.class)
    public ResponseEntity<Object> handleInvalidProductException(InvalidProductException ex) {
        ResponseObject<Object> responseObject = new ResponseObject<>();
        responseObject.setMessage("error");
        responseObject.setData(ex.getParam());
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidOrderProductException.class)
    public ResponseEntity<Object> handleInvalidOrderProductException(InvalidOrderProductException ex) {
        ResponseObject<Object> responseObject = new ResponseObject<>();
        responseObject.setMessage("error");
        responseObject.setData(ex.getParam());
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PermissionDeniedException.class)
    public ResponseEntity<Object> handlePermissionDeniedException(InvalidOrderProductException ex) {
        ResponseObject<String> responseObject = new ResponseObject<>();
        responseObject.setMessage("error");
        responseObject.setData(ex.getMessage());
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
}
