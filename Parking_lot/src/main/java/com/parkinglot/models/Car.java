package main.java.com.parkinglot.models;
import main.java.com.parkinglot.enums.VehicleType;

public class Car extends Vehicle {
    public Car(String licensePlate) { super(licensePlate, VehicleType.CAR); }
}