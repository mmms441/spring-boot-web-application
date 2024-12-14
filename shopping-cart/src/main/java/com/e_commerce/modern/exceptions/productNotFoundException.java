package com.e_commerce.modern.exceptions;

public class productNotFoundException extends  RuntimeException{
    public productNotFoundException(String message ){
        super(message);
    }
}
