package com.example.demo.exception;

public class NameNotFoundException extends RuntimeException{
    public NameNotFoundException(String error) {
        super(error);
    }
}
