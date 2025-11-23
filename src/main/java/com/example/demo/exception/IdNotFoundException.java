package com.example.demo.exception;

public class IdNotFoundException extends  RuntimeException{
    public IdNotFoundException (String error){
        super(error);
    }
}
