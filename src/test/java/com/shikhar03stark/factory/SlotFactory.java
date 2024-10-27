package com.shikhar03stark.factory;

import com.shikhar03stark.parkinglot.exceptions.SlotNotEmptyException;
import com.shikhar03stark.parkinglot.models.Slot;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlotFactory {

    private static Slot getEmptySlotWithId(String id) {
        return new Slot(id, null);
    }

    public static List<Slot> getContinuousOrderingSlots(int numOfSlots) {
        return IntStream
                .range(1, numOfSlots+1)
                .mapToObj(id -> getEmptySlotWithId(String.valueOf(id)))
                .collect(Collectors.toList());
    }

    public static List<Slot> getContinuousOrderingSlotsWithFirstOccupied(int numOfSlots, int occupiedSlots) {
        final List<Slot> allSlots = getContinuousOrderingSlots(numOfSlots);
        for(int slotIdx = 0; slotIdx < Math.min(numOfSlots, occupiedSlots); slotIdx++) {
            try {
                allSlots.get(slotIdx).parkCar(CarFactory.getNewCar());
            } catch (SlotNotEmptyException e) {
                throw new RuntimeException(e);
            }
        }
        return allSlots;
    }
}
