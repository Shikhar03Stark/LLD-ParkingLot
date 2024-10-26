package com.shikhar03stark;


import com.shikhar03stark.parkinglot.api.ParkingLotManager;
import com.shikhar03stark.parkinglot.api.ParkingLotStatus;
import com.shikhar03stark.parkinglot.api.impl.CarParkingLot;
import com.shikhar03stark.parkinglot.models.*;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderStrategy;
import com.shikhar03stark.parkinglot.parkingstrategy.ParkingOrderType;
import com.shikhar03stark.parkinglot.parkingstrategy.strategy.ClosestFirstParkingOrderStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        final List<Slot> parkingSlots = getParkingSlots(100);
        final ParkingLot parkingLot = new ParkingLot(parkingSlots);
        final SlotAssigner slotAssigner = new SlotAssigner(getParkingStrategyMap());
        final CarParkingLot carParkingLot = new CarParkingLot(parkingLot, slotAssigner);

        simulate(carParkingLot, carParkingLot);
    }

    private static void simulate(ParkingLotManager parkingLotManager, ParkingLotStatus parkingLotStatus) {
        try {
            Car car1 = getNewCar(), car2 = getNewCar(), car3 = getNewCar();
            Ticket ticket1 = parkingLotManager.parkCar(car1);
            System.out.println(ticket1.slot().getId());
            System.out.println(parkingLotStatus.totalFilledSlots());

            Ticket ticket2 = parkingLotManager.parkCar(car2);
            System.out.println(ticket2.slot().getId());

            parkingLotManager.unParkCar(ticket1);
            System.out.println(parkingLotStatus.totalFilledSlots());

            Ticket ticket3 = parkingLotManager.parkCar(car3);
            System.out.println(ticket3.slot().getId());
        } catch (Exception _) {

        }
    }

    private static Map<ParkingOrderType, ParkingOrderStrategy> getParkingStrategyMap() {
        final Map<ParkingOrderType, ParkingOrderStrategy> map = new HashMap<>();
        map.put(ParkingOrderType.CLOSEST_TO_ENTRY, new ClosestFirstParkingOrderStrategy());
        return map;
    }

    private static List<Slot> getParkingSlots(int count) {
        return IntStream
                .range(1, count+1)
                .mapToObj(id -> new Slot(String.valueOf(id), null))
                .collect(Collectors.toList());
    }

    private static String generateNewRegNumber() {
        final Random random = new Random();
        final StringBuilder builder = new StringBuilder();

        builder.append((char) (65 + random.nextInt(26)));
        builder.append((char) (65 + random.nextInt(26)));

        builder.append(' ');

        builder.append(String.format("%4d", random.nextInt(9999)));
        return builder.toString();
    }

    private static Car getNewCar() {
        final List<String> colours = List.of("RED", "GREY", "YELLOW", "BLACK", "WHITE");
        final Random random = new Random();
        return new Car(colours.get(random.nextInt(colours.size())), generateNewRegNumber());
    }

}