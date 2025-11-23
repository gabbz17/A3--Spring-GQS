package com.example.demo.exception;

public class ConstraintViolationException extends RuntimeException{
    public ConstraintViolationException(String error){
        super(error);
    }
}
