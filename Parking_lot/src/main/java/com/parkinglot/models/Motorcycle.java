package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.VehicleType;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) { super(licensePlate, VehicleType.MOTORCYCLE); }
}