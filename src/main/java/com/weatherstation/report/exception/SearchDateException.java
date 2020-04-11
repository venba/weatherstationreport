package com.weatherstation.report.exception;


public class SearchDateException extends IllegalArgumentException {
    public SearchDateException(String errorMessage) {
        super(errorMessage);
    }
}