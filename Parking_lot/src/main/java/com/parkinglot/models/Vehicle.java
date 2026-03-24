package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.VehicleType;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
}

// Making it abstract prevents another developer from doing Vehicle v = new Vehicle(...).
// It forces them to instantiate a specific, concrete type.

// It defines a contract. It says, "Anything that calls itself a Vehicle in this system must have a license plate and a type, but I will let the specific subclasses define the details."