package com.shikhar03stark.parkinglot.models;

import com.shikhar03stark.parkinglot.exceptions.SlotNotEmptyException;

public class Slot {
    private final String id;
    private Car parkedCar;

    public Slot(String id, Car parkedCar) {
        this.id = id;
        this.parkedCar = parkedCar;
    }

    public void parkCar(Car car) throws SlotNotEmptyException {
        if (parkedCar != null) throw new SlotNotEmptyException(id, null);
        parkedCar = car;
    }

    public void unParkCar() {
        parkedCar = null;
    }

    public boolean hasParkedCar() {
        return parkedCar != null;
    }

    public String getId() {
        return this.id;
    }

    public Car getParkedCar() {
        return parkedCar;
    }
}
