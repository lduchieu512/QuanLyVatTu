package com.example.medicalsupplieswebsite.dto;

import java.util.List;

public class InvalidDataException extends RuntimeException {
    private List<ValidationError> errors;

    public InvalidDataException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
