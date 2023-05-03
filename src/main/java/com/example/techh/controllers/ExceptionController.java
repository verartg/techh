package com.example.techh.controllers;
import com.example.techh.exceptions.RecordNotFoundException;
import com.example.techh.exceptions.TooManyCharException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TooManyCharException.class)
    public ResponseEntity<Object> exception(TooManyCharException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public ResponseEntity<Object> exception (IndexOutOfBoundsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }


}
