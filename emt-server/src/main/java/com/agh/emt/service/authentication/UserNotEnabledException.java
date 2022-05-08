package com.agh.emt.service.authentication;

public class UserNotEnabledException extends Exception{
    public UserNotEnabledException(String message) {
        super(message);
    }
}
