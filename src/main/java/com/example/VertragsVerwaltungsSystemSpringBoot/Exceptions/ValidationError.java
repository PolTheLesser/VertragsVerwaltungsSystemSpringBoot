package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

public class ValidationError {

    public String attribute;
    public String message;

    public ValidationError(String attribute, String message) {
        attribute = this.attribute;
        message = this.message;
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
