package com.shikhar03stark.parkinglot.parkingstrategy.strategy;

import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.models.Slot;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderStrategy;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ClosestFirstParkingOrderStrategy implements ParkingOrderStrategy {
    private final ParkingOrderType parkingOrderType = ParkingOrderType.CLOSEST_TO_ENTRY;


    @Override
    public ParkingOrderType getParkingOrderType() {
        return parkingOrderType;
    }

    @Override
    public String getNextEmptySlotId(List<Slot> slots) throws ParkingLotFullException {
        final Optional<String> nextSlotIdOptional = slots
                .stream()
                .sorted(Comparator.comparing(slot -> Integer.parseInt(slot.getId())))
                .filter(slot -> !slot.hasParkedCar())
                .map(Slot::getId)
                .findFirst();

        return nextSlotIdOptional.orElseThrow(ParkingLotFullException::new);
    }
}
