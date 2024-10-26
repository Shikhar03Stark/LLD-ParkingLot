package com.shikhar03stark.parkinglot.api;

import com.shikhar03stark.parkinglot.exceptions.CarNotParkedException;
import com.shikhar03stark.parkinglot.models.Car;
import com.shikhar03stark.parkinglot.models.Slot;

import java.util.List;

public interface ParkingLotStatus {
    // slots info
    int totalNumberOfSlots();
    int totalAvailableSlots();
    int totalFilledSlots();

    // Govt regulations
    List<Car> carsWithColour(String colour);
    Slot slotOfCarWithReg(String registrationNumber) throws CarNotParkedException;
    List<Slot> slotsWithParkedCarColour(String colour);
}
