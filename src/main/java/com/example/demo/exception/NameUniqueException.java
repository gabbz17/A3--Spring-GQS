package com.example.demo.exception;

public class NameUniqueException extends RuntimeException{
    public NameUniqueException(String error){
        super(error);
    }
}
