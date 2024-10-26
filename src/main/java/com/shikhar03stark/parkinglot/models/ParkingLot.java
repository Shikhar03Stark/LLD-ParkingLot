package com.shikhar03stark.parkinglot.models;

import com.shikhar03stark.parkinglot.exceptions.SlotNotEmptyException;

import java.util.List;
import java.util.Optional;

public class ParkingLot {
    private final List<Slot> slots;

    public ParkingLot(List<Slot> slots) {
        this.slots = slots;
    }

    public Ticket parkCarAt(Car car, String slotId) throws SlotNotEmptyException {
        final Optional<Slot> freeSlotOptional = this.slots.stream()
                .filter(slot -> slot.getId().equals(slotId))
                .findFirst();
        if(freeSlotOptional.isEmpty()) throw new SlotNotEmptyException(slotId, null);
        freeSlotOptional.get().parkCar(car);
        return new Ticket(car, freeSlotOptional.get());
    }

    public void exitCar(Ticket ticket) {
        final Slot ticketSlot = ticket.slot();
        final Optional<Slot> slotOptional = this.slots.stream()
                .filter(slot -> slot.getId().equals(ticketSlot.getId()) && slot.hasParkedCar())
                .findFirst();

        slotOptional.ifPresent(Slot::unParkCar);
    }

    public List<Slot> getSlots() {
        return slots;
    }
}
