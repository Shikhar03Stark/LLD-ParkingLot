package com.shikhar03stark.parkinglot.api;

import com.shikhar03stark.parkinglot.exceptions.InvalidParkingOrderStrategyException;
import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.exceptions.SlotNotEmptyException;
import com.shikhar03stark.parkinglot.models.Car;
import com.shikhar03stark.parkinglot.models.Ticket;

public interface ParkingLotManager {
    Ticket parkCar(Car car) throws ParkingLotFullException, InvalidParkingOrderStrategyException;
    void unParkCar(Ticket ticket);
}
