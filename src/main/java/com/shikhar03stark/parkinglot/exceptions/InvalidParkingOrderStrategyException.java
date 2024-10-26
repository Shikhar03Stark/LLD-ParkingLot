package com.shikhar03stark.parkinglot.exceptions;

import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;

public class InvalidParkingOrderStrategyException extends Exception {
    private final ParkingOrderType parkingOrderType;

    public InvalidParkingOrderStrategyException(ParkingOrderType parkingOrderType) {
        super();
        this.parkingOrderType = parkingOrderType;
    }

    @Override
    public String getMessage() {
        return String.format("%s is invalid or not implemented", parkingOrderType);
    }
}
