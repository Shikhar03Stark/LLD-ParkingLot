package com.shikhar03stark.parkinglot.api.impl;

import com.shikhar03stark.parkinglot.api.ParkingLotManager;
import com.shikhar03stark.parkinglot.api.ParkingLotStatus;
import com.shikhar03stark.parkinglot.exceptions.CarNotParkedException;
import com.shikhar03stark.parkinglot.exceptions.InvalidParkingOrderStrategyException;
import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.exceptions.SlotNotEmptyException;
import com.shikhar03stark.parkinglot.models.*;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarParkingLot implements ParkingLotManager, ParkingLotStatus {

    private final ParkingLot parkingLot;
    private final SlotAssigner slotAssigner;

    public CarParkingLot(ParkingLot parkingLot, SlotAssigner assigner) {
        this.parkingLot = parkingLot;
        this.slotAssigner = assigner;
    }

    @Override
    public Ticket parkCar(Car car) throws ParkingLotFullException, InvalidParkingOrderStrategyException {
        final String freeSlotId = slotAssigner.getNextAvailableSlot(ParkingOrderType.CLOSEST_TO_ENTRY, parkingLot.getSlots());
        try {
            return parkingLot.parkCarAt(car, freeSlotId);
        } catch (SlotNotEmptyException e) {
            throw new ParkingLotFullException();
        }

    }

    @Override
    public void unParkCar(Ticket ticket) {
        this.parkingLot.exitCar(ticket);
    }

    @Override
    public int totalNumberOfSlots() {
        return this.parkingLot.getSlots().size();
    }

    @Override
    public int totalAvailableSlots() {
        return totalNumberOfSlots() - totalFilledSlots();
    }

    @Override
    public int totalFilledSlots() {
        return (int) this.parkingLot.getSlots().stream()
                .filter(Slot::hasParkedCar)
                .count();
    }

    @Override
    public List<Car> carsWithColour(String colour) {
        return this.parkingLot.getSlots().stream()
                .filter(Slot::hasParkedCar)
                .map(Slot::getParkedCar)
                .collect(Collectors.toList());
    }

    @Override
    public Slot slotOfCarWithReg(String registrationNumber) throws CarNotParkedException {
        final Optional<Slot> slotOptional = this.parkingLot.getSlots().stream()
                .filter(Slot::hasParkedCar)
                .map(slot -> Map.entry(slot, slot.getParkedCar()))
                .filter(pair -> pair.getValue().registrationNumber().equals(registrationNumber))
                .map(Map.Entry::getKey)
                .findFirst();

        return slotOptional.orElseThrow(() -> new CarNotParkedException(registrationNumber));
    }

    @Override
    public List<Slot> slotsWithParkedCarColour(String colour) {
        return this.parkingLot.getSlots().stream()
                .filter(Slot::hasParkedCar)
                .map(slot -> Map.entry(slot, slot.getParkedCar()))
                .filter(pair -> pair.getValue().colour().equals(colour))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
