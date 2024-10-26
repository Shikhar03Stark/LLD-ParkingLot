package com.shikhar03stark.parkinglot.exceptions;

public class SlotNotEmptyException extends Exception {
    private final String slotId;

    public SlotNotEmptyException(String slotId, Throwable cause) {
        super(cause);
        this.slotId = slotId;
    }

    @Override
    public String getMessage() {
        return String.format("%s is already occupied by another vehicle", slotId);
    }
}
