package com.example.techh.exceptions;

public class TooManyCharException extends RuntimeException {
    public TooManyCharException(){
        super();
    }

    public TooManyCharException(String message){
        super(message);
    }


}