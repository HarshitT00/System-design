package main.java.com.parkinglot.models;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot allocatedSpot;
    private final LocalDateTime entryTime;

    public Ticket(Vehicle vehicle, ParkingSpot allocatedSpot) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8); // Generate a short ID
        this.vehicle = vehicle;
        this.allocatedSpot = allocatedSpot;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getAllocatedSpot() { return allocatedSpot; }
    public LocalDateTime getEntryTime() { return entryTime; }
}