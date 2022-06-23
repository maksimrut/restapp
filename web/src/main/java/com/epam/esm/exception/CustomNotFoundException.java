package com.epam.esm.exception;

/**
 * Instantiates a Custom not found exception.
 */
public class CustomNotFoundException extends RuntimeException {

    private Long resourceId;

    public CustomNotFoundException(Long resourceId) {
        this.resourceId = resourceId;
    }

    public CustomNotFoundException(String message) {
        super(message);
    }

    public Long getResourceId() {
        return resourceId;
    }
}
