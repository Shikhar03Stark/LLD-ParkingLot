package com.shikhar03stark.parkinglot.api.impl;

import com.shikhar03stark.factory.CarFactory;
import com.shikhar03stark.factory.SlotFactory;
import com.shikhar03stark.parkinglot.exceptions.CarNotParkedException;
import com.shikhar03stark.parkinglot.exceptions.InvalidParkingOrderStrategyException;
import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.models.*;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderStrategy;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;
import com.shikhar03stark.parkinglot.parkingstrategy.strategy.ClosestFirstParkingOrderStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CarParkingLotTest {

    private CarParkingLot getCarParkingLot(List<Slot> slots) {
        final Map<ParkingOrderType, ParkingOrderStrategy> parkingOrderTypeParkingOrderStrategyMap = new HashMap<>();
        parkingOrderTypeParkingOrderStrategyMap.put(ParkingOrderType.CLOSEST_TO_ENTRY, new ClosestFirstParkingOrderStrategy());
        final SlotAssigner slotAssigner = new SlotAssigner(parkingOrderTypeParkingOrderStrategyMap);
        final ParkingLot parkingLot = new ParkingLot(slots);
        return new CarParkingLot(parkingLot, slotAssigner);
    }

    private CarParkingLot getCarParkingLotWithEmptySlots(int numOfSlots) {
        final List<Slot> emptySlots = SlotFactory.getContinuousOrderingSlots(numOfSlots);
        return getCarParkingLot(emptySlots);
    }

    private List<Car> cars = List.of();

    @BeforeEach
    public void beforeEach() {
        cars = IntStream
                .range(0, 10)
                .mapToObj(_ -> CarFactory.getNewCar())
                .toList();
    }

    @Test
    public void GetParkingLotSummaryTest() throws CarNotParkedException {
        final CarParkingLot parkingLot = getCarParkingLotWithEmptySlots(15);
        final List<Ticket> tickets = cars
                .stream()
                .map(car -> {
                    try {
                        return parkingLot.parkCar(car);
                    } catch (Exception _) {}
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        List<Car> parkedCarsWithColour = parkingLot.carsWithColour(CarFactory.colours.get(1));
        int parkedCarsWithColourCount = (int) cars.stream().filter(car -> car.colour().equals(CarFactory.colours.get(1))).count();
        Assertions.assertEquals(parkedCarsWithColourCount, parkedCarsWithColour.size());

        final Slot slotOfCar = parkingLot.slotOfCarWithReg(cars.getFirst().registrationNumber());
        Assertions.assertEquals(slotOfCar.getParkedCar(), cars.getFirst());

        final List<Slot> slotsOfSameColour = parkingLot.slotsWithParkedCarColour(cars.getFirst().colour());
        final List<Slot> expectedSlots = tickets.stream()
                .map(Ticket::slot)
                .filter(slot -> slot.hasParkedCar() && slot.getParkedCar().colour().equals(cars.getFirst().colour()))
                .toList();

        Assertions.assertEquals(expectedSlots.size(), slotsOfSameColour.size());
        Assertions.assertTrue(slotsOfSameColour.containsAll(expectedSlots));
    }
}