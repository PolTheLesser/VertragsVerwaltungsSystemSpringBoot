package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

public class ValidationException {

    public String attribute;
    public String message;

    public ValidationException(String attribute, String message) {
        this.attribute = attribute;
        this.message = message;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "attribute='" + attribute + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
