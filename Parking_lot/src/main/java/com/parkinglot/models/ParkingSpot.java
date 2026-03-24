package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.SpotType;

public class ParkingSpot {
    private final int spotId;
    private final SpotType spotType;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
    }

    public boolean isFree() { return parkedVehicle == null; }
    public SpotType getSpotType() { return spotType; }
    public Vehicle getVehicle() { return parkedVehicle; }
    public int getSpotId() { return spotId; }

    public void assignVehicle(Vehicle vehicle) { this.parkedVehicle = vehicle; }
    public void removeVehicle() { this.parkedVehicle = null; }
}