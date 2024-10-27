package com.shikhar03stark.parkinglot.parkingstrategy.strategy;

import com.shikhar03stark.factory.CarFactory;
import com.shikhar03stark.factory.SlotFactory;
import com.shikhar03stark.parkinglot.exceptions.ParkingLotFullException;
import com.shikhar03stark.parkinglot.models.Car;
import com.shikhar03stark.parkinglot.models.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClosestFirstParkingOrderStrategyTest {

    private final ClosestFirstParkingOrderStrategy parkingOrder = new ClosestFirstParkingOrderStrategy();


    @Test
    public void GetFirstEmptyTest() throws ParkingLotFullException {
        final List<Slot> slots = SlotFactory.getContinuousOrderingSlots(5);
        String slotId = parkingOrder.getNextEmptySlotId(slots);
        Assertions.assertEquals("1", slotId);
    }

    @Test
    public void GetFirstEmptyWithOccupiedSlotTest() throws ParkingLotFullException {
        final List<Slot> slots = SlotFactory.getContinuousOrderingSlotsWithFirstOccupied(5, 2);
        String slotId = parkingOrder.getNextEmptySlotId(slots);
        Assertions.assertEquals("3", slotId);
    }
}