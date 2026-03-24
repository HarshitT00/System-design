package main.java.com.parkinglot.strategy;
import main.java.com.parkinglot.models.Level;
import main.java.com.parkinglot.models.ParkingSpot;
import main.java.com.parkinglot.enums.SpotType;
import main.java.com.parkinglot.enums.VehicleType;
import java.util.List;
import java.util.Optional;

public class NearestSpotStrategy implements ParkingStrategy {
    @Override
    public Optional<ParkingSpot> findSpot(List<Level> levels, VehicleType vehicleType) {
        SpotType requiredSpotType = getRequiredSpotType(vehicleType);

        for (Level level : levels) {
            for (ParkingSpot spot : level.getSpots()) {
                if (spot.isFree() && spot.getSpotType() == requiredSpotType) {
                    return Optional.of(spot);
                }
            }
        }
        return Optional.empty();
    }

    private SpotType getRequiredSpotType(VehicleType type) {
        switch (type) {
            case MOTORCYCLE: return SpotType.MOTORBIKE;
            case CAR: return SpotType.COMPACT;
            case TRUCK: return SpotType.LARGE;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

// Putting getRequiredSpotType inside NearestSpotStrategy makes sense because the mapping of "Vehicle to Spot" is actually a rule of the strategy,
// not necessarily a universal truth of the vehicle itself.
//For example, imagine you write a new FlexibleParkingStrategy for night shifts where Motorcycles are allowed to park in Compact spots if the lot is empty.