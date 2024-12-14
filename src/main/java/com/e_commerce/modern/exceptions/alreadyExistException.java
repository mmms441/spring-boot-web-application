package com.e_commerce.modern.exceptions;

public class alreadyExistException extends RuntimeException{
    public alreadyExistException(String message){
        super(message);
    }
}
