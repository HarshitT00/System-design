package main.java.com.parkinglot;

import main.java.com.parkinglot.core.ParkingLot;
import main.java.com.parkinglot.models.*;
import main.java.com.parkinglot.strategy.HourlyPaymentStrategy;
import main.java.com.parkinglot.strategy.NearestSpotStrategy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyRunner {
    public static void main(String[] args) {
        // 1. Setup the Lot
        ParkingLot lot = ParkingLot.getInstance();
        lot.setStrategies(new NearestSpotStrategy(), new HourlyPaymentStrategy());

        // CRITICAL: We only have exactly TWO compact spots available.
        lot.addLevel(new Level(1, 0, 2, 0));

        System.out.println("--- Starting Concurrency Test ---");
        System.out.println("Lot capacity: 2 Compact Spots.");
        System.out.println("10 Cars approaching simultaneously...\n");

        int numberOfCars = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCars);

        // This latch acts as a starting gun. It forces threads to wait until we say "GO!"
        CountDownLatch startGun = new CountDownLatch(1);

        for (int i = 1; i <= numberOfCars; i++) {
            String licensePlate = "CAR-" + i;
            executor.submit(() -> {
                try {
                    // All threads pause here and wait for the gun to fire
                    startGun.await();

                    // The moment the gun fires, all 10 threads hit this method simultaneously
                    lot.parkVehicle(new Car(licensePlate));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Fire the starting gun!
        startGun.countDown();

        // Shut down the executor safely after all tasks finish
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Concurrency Test Complete ---");
    }
}