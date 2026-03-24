package main.java.com.parkinglot.core;
import main.java.com.parkinglot.models.*;
import main.java.com.parkinglot.strategy.*;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private static volatile ParkingLot instance; // volatile prevents CPU caching
    private final List<Level> levels;
    private ParkingStrategy parkingStrategy;
    private PaymentStrategy paymentStrategy;

    private ParkingLot() { this.levels = new ArrayList<>(); }

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) { // Double-checked locking
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public void setStrategies(ParkingStrategy parkingStrategy, PaymentStrategy paymentStrategy) {
        this.parkingStrategy = parkingStrategy;
        this.paymentStrategy = paymentStrategy;
    }

    public void addLevel(Level level) { levels.add(level); }

    // THE LOCK: Only one thread can execute this at a time
    public synchronized Ticket parkVehicle(Vehicle vehicle) {
        if (parkingStrategy == null) throw new IllegalStateException("Strategies not set!");

        Optional<ParkingSpot> spotOpt = parkingStrategy.findSpot(levels, vehicle.getType());

        if (spotOpt.isPresent()) {
            ParkingSpot spot = spotOpt.get();
            spot.assignVehicle(vehicle);
            Ticket ticket = new Ticket(vehicle, spot);
            System.out.println("✅ " + vehicle.getType() + " [" + vehicle.getLicensePlate() +
                    "] parked at Spot " + spot.getSpotId() + " on Thread: " + Thread.currentThread().getName());
            return ticket;
        }

        System.out.println("❌ Lot Full for " + vehicle.getType() + " [" + vehicle.getLicensePlate() + "]");
        return null;
    }

    // THE LOCK: Prevents conflicts if someone is exiting while someone else is parking
    public synchronized double processExit(Ticket ticket) {
        if (paymentStrategy == null) throw new IllegalStateException("Strategies not set!");

        LocalDateTime exitTime = LocalDateTime.now();
        double fee = paymentStrategy.calculateFee(ticket, exitTime);

        ticket.getAllocatedSpot().removeVehicle();
        System.out.println("🚗 " + ticket.getVehicle().getType() + " [" + ticket.getVehicle().getLicensePlate() +
                "] exited. Fee: $" + fee + " on Thread: " + Thread.currentThread().getName());
        return fee;
    }
}

//volatile: This has to do with how computer processors work.
// Threads often cache variables in their own local CPU memory for performance.
// volatile tells the Java Virtual Machine: "Never cache this variable. Always read it directly from the main RAM."
// This ensures that if Thread A creates the ParkingLot instance,
// Thread B instantly sees it, preventing Thread B from accidentally creating a second ParkingLot.
// synchronized Only one thread can execute this method at a time.