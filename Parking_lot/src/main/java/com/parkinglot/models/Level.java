package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.SpotType;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int floorNumber;
    private final List<ParkingSpot> spots;

    public Level(int floorNumber, int motorbikeSpots, int compactSpots, int largeSpots) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
        int idCounter = 1;

        for (int i = 0; i < motorbikeSpots; i++) spots.add(new ParkingSpot(idCounter++, SpotType.MOTORBIKE));
        for (int i = 0; i < compactSpots; i++) spots.add(new ParkingSpot(idCounter++, SpotType.COMPACT));
        for (int i = 0; i < largeSpots; i++) spots.add(new ParkingSpot(idCounter++, SpotType.LARGE));
    }

    public List<ParkingSpot> getSpots() { return spots; }
    public int getFloorNumber() { return floorNumber; }
}