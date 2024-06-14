package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoContractFoundException extends RuntimeException {

    public NoContractFoundException() {
        super();
    }

    public NoContractFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoContractFoundException(String message) {
        super(message);
    }

    public NoContractFoundException(Throwable cause) {
        super(cause);
    }
}
