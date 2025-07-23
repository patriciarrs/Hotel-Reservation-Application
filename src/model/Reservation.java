package model;

import java.util.Date;

/**
 * Represents a customer reservation of a hotel room. Provides its properties.
 */
final public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkIn;
    private final Date checkOut;

    public Reservation(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
