package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException() {
        super();
    }

    public ContractNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContractNotFoundException(String message) {
        super(message);
    }

    public ContractNotFoundException(Throwable cause) {
        super(cause);
    }
}
