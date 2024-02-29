package com.nal.pdfcvbuilder.pdfCvBuilderExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
