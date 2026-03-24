package main.java.com.parkinglot.strategy;
import main.java.com.parkinglot.models.Level;
import main.java.com.parkinglot.models.ParkingSpot;
import main.java.com.parkinglot.enums.VehicleType;
import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(List<Level> levels, VehicleType vehicleType);
}