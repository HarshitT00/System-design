package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.VehicleType;

public class Truck extends Vehicle {
    public Truck(String licensePlate) { super(licensePlate, VehicleType.TRUCK); }
}