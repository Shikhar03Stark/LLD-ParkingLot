package com.shikhar03stark.parkinglot.exceptions;

public class CarNotParkedException extends Exception {
    private final String registrationNumber;

    public CarNotParkedException(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String getMessage() {
        return String.format("Car with %s not found in parking lot", registrationNumber);
    }
}
