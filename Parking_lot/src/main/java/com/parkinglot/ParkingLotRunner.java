package main.java.com.parkinglot;

import main.java.com.parkinglot.core.ParkingLot;
import main.java.com.parkinglot.models.*;
import main.java.com.parkinglot.strategy.HourlyPaymentStrategy;
import main.java.com.parkinglot.strategy.NearestSpotStrategy;

public class ParkingLotRunner {
    public static void main(String[] args) {
        // 1. Initialize the Singleton Lot
        ParkingLot lot = ParkingLot.getInstance();

        // 2. Inject BOTH strategies (Parking algorithm + Pricing model)
        lot.setStrategies(new NearestSpotStrategy(), new HourlyPaymentStrategy());

        // 3. Build the physical lot (Level 1: 1 Motorbike, 2 Compact, 1 Large)
        lot.addLevel(new Level(1, 1, 2, 1));

        // 4. Create Vehicles
        Vehicle moto = new Motorcycle("MOTO-123");
        Vehicle car1 = new Car("CAR-100");
        Vehicle car2 = new Car("CAR-200");
        Vehicle car3 = new Car("CAR-300"); // Will fail initially (only 2 compact spots)
        Vehicle truck = new Truck("TRK-999");

        System.out.println("--- Entering Parking Lot ---");
        Ticket motoTicket = lot.parkVehicle(moto);
        Ticket car1Ticket = lot.parkVehicle(car1);
        Ticket car2Ticket = lot.parkVehicle(car2);
        Ticket car3Ticket = lot.parkVehicle(car3);

        Ticket truckTicket = lot.parkVehicle(truck);

        System.out.println("\n--- Simulating Time Passing... ---");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Exiting Parking Lot ---");

        if (car1Ticket != null) lot.processExit(car1Ticket);
        if (motoTicket != null) lot.processExit(motoTicket);

        System.out.println("\n--- Late Arrival ---");

        Ticket car3TicketRetry = lot.parkVehicle(car3);

        System.out.println("\n--- Final Exits ---");

        if (car2Ticket != null) lot.processExit(car2Ticket);
        if (truckTicket != null) lot.processExit(truckTicket);
        if (car3TicketRetry != null) lot.processExit(car3TicketRetry);
    }
}