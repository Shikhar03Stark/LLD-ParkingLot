package com.shikhar03stark.factory;

import com.shikhar03stark.parkinglot.models.Car;

import java.util.List;
import java.util.Random;

public class CarFactory {
    public static final List<String> colours = List.of("RED", "BLUE", "BLACK", "YELLOW", "ORANGE", "PINK");
    public static final List<String> stateCode = List.of("UP", "PB", "UK", "KA", "TN", "HR");

    private static int getRandomIndex(int size) {
        final Random random = new Random();
        return random.nextInt(size);
    }

    private static String getRandomStateCode() {
        return stateCode.get(getRandomIndex(stateCode.size()));
    }

    private static String getRandomDigits(int numOfDigits) {
        final StringBuilder result = new StringBuilder("0".repeat(Math.max(0, numOfDigits)));
        int upperLimit = (int) Math.pow(10, numOfDigits);

        final String prePad = String.valueOf(getRandomIndex(upperLimit));

        for(int i = result.length()-1, j = prePad.length()-1; i>=0 && j>=0; i--, j--) {
            result.setCharAt(i, prePad.charAt(j));
        }

        return result.toString();

    }

    private static String getRandomColour() {
        return colours.get(getRandomIndex(colours.size()));
    }

    private static String getRandomRegistrationNumber() {
        return getRandomStateCode() +
                ' ' +
                getRandomDigits(2) +
                ' ' +
                getRandomDigits(4);
    }

    public static Car getNewCar() {
        return new Car(getRandomColour(), getRandomRegistrationNumber());
    }


}
