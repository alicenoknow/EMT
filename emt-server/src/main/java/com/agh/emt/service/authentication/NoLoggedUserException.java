package com.agh.emt.service.authentication;

public class NoLoggedUserException extends Exception {
    public NoLoggedUserException(String message) {
        super(message);
    }
}
