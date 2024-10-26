package com.shikhar03stark.parkinglot.models;

import com.shikhar03stark.parkinglot.exceptions.InvalidParkingOrderStrategyException;
import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderStrategy;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;

import java.util.List;
import java.util.Map;

public class SlotAssigner {

    private final Map<ParkingOrderType, ParkingOrderStrategy> assignmentStrategy;

    public SlotAssigner(Map<ParkingOrderType, ParkingOrderStrategy> assignmentStrategy) {
        this.assignmentStrategy = assignmentStrategy;
    }

    public String getNextAvailableSlot(
            ParkingOrderType parkingOrderType,
            List<Slot> slots) throws ParkingLotFullException, InvalidParkingOrderStrategyException {

        final ParkingOrderStrategy strategy = assignmentStrategy.get(parkingOrderType);
        if (strategy == null) throw new InvalidParkingOrderStrategyException(parkingOrderType);
        return strategy.getNextEmptySlotId(slots);
    }
}
