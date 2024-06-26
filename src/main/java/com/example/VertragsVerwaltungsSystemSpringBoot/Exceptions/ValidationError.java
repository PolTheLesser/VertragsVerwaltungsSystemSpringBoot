package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

public class ValidationError {

    public String attribute;
    public String message;

    public ValidationError(String attribute, String message) {
        this.attribute = attribute;
        this.message = message;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "attribute='" + attribute + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
