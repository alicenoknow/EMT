package com.agh.emt.service.email_sender;

public class NoSuchConfirmationTokenException extends Exception {
    public NoSuchConfirmationTokenException(String message) {
        super(message);
    }
}
