package com.epam.esm.exception;

/**
 * The type Custom error class
 * for message generation for the client
 *
 * @author Maksim Rutkouski
 */
public class CustomError {
    private final int errorCode;
    private final String errorMessage;

    public CustomError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
