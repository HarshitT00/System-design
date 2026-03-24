package com.parkinglot;

import com.parkinglot.core.ParkingLot;
import com.parkinglot.models.*;
import com.parkinglot.strategy.NearestSpotStrategy;

public class ParkingLotRunner {
    public static void main(String[] args) {
        ParkingLot lot = ParkingLot.getInstance();
        lot.setParkingStrategy(new NearestSpotStrategy());
        lot.addLevel(new Level(1, 1, 2, 0));

        Vehicle moto1 = new Motorcycle("MOTO-123");
        Vehicle car1 = new Car("CAR-100");
        Vehicle car2 = new Car("CAR-200");
        Vehicle car3 = new Car("CAR-300");

        System.out.println("--- Entering Parking Lot ---");
        lot.parkVehicle(moto1);
        lot.parkVehicle(car1);
        lot.parkVehicle(car2);
        lot.parkVehicle(car3);

        System.out.println("\n--- Exiting Parking Lot ---");
        lot.removeVehicle(car1);

        System.out.println("\n--- Late Arrival ---");
        lot.parkVehicle(car3);
    }
}