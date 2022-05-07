package com.agh.emt.utils.authentication.signup_validator;

public class PasswordNotMatchingException extends Exception {
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
