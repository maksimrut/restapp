package com.epam.esm.exception;

/**
 * The type Custom internal exception.
 * For handling all non-expected exceptions
 */
public class CustomInternalException extends RuntimeException {

    private String message;

    public CustomInternalException(String message) {
        this.message = message;
    }
}
