package com.hatchways.assignment.exception;


public class ApiException extends RuntimeException{
    public ApiException(String error) {
        super(error);
    }
}
