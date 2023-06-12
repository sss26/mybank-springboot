package com.smthasa.mybankspringboot.dto;

import java.util.List;

public class ErrorDto {

    private String message;

    private List<String> validationFailures;

    public ErrorDto(String message, List<String> validationFailures) {
        this.message = message;
        this.validationFailures = validationFailures;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getValidationFailures() {
        return validationFailures;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValidationFailures(List<String> validationFailures) {
        this.validationFailures = validationFailures;
    }

}
