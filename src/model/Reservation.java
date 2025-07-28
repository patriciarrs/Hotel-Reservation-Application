package model;

import java.util.Date;
import java.util.Objects;

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

    public Customer getCustomer() {
        return customer;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(room, that.room) &&
                Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkIn, checkOut);
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
