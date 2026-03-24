package main.java.com.parkinglot.strategy;
import main.java.com.parkinglot.models.Ticket;
import java.time.LocalDateTime;

public interface PaymentStrategy {
    double calculateFee(Ticket ticket, LocalDateTime exitTime);
}