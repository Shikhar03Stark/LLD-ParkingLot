package com.shikhar03stark.parkinglot.parkingstrategy;

import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.models.Slot;

import java.util.List;

public interface ParkingOrderStrategy {
    ParkingOrderType getParkingOrderType();

    String getNextEmptySlotId(List<Slot> slots) throws ParkingLotFullException;
}
