package com.epam.esm.exception;

/**
 * The type Resource duplicate exception.
 */
public class ResourceDuplicateException extends RuntimeException {

    private final String uniqueField;
    private final Object uniqueFieldValue;

    public ResourceDuplicateException(String uniqueField, Object uniqueFieldValue) {
        this.uniqueField = uniqueField;
        this.uniqueFieldValue = uniqueFieldValue;
    }

    public String getUniqueField() {
        return uniqueField;
    }

    public Object getUniqueFieldValue() {
        return uniqueFieldValue;
    }
}
