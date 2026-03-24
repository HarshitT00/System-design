package main.java.com.parkinglot.strategy;
import main.java.com.parkinglot.models.Ticket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HourlyPaymentStrategy implements PaymentStrategy {
    @Override
    public double calculateFee(Ticket ticket, LocalDateTime exitTime) {
        long minutesParked = ChronoUnit.MINUTES.between(ticket.getEntryTime(), exitTime);
        long hoursParked = (long) Math.ceil(minutesParked / 60.0);
        long chargeableHours = Math.max(1, hoursParked);

        switch (ticket.getVehicle().getType()) {
            case MOTORCYCLE:
                return chargeableHours * 10.0;
            case CAR:
                return chargeableHours * 20.0;
            case TRUCK:
                return chargeableHours * 50.0;
            default:
                return 0.0;
        }
    }
}