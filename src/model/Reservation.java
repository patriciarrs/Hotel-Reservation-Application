package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the domain of a reservation. Models the data object for the hotel reservation application domain.
 */
final public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Reservation(Customer customer, IRoom room, LocalDate checkIn, LocalDate checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
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
        return """
                Reservation
                $firstName $lastName
                Room: $number - $type
                Price: $price price per night
                Check-in date: $checkIn
                Check-out date: $checkOut
                """
                .replace("$firstName", customer.getFirstName())
                .replace("$lastName", customer.getLastName())
                .replace("$number", room.getNumber())
                .replace("$type", room.getType().name())
                .replace("$price", room.getPrice().toString())
                .replace("$checkIn", checkIn.toString())
                .replace("$checkOut", checkOut.toString());
    }
}
