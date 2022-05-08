package com.agh.emt.service.authentication.email_sender;

public class NoSuchConfirmationTokenException extends Exception {
    public NoSuchConfirmationTokenException(String message) {
        super(message);
    }
}
