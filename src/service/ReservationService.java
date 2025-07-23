package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Provides reservation-related operations such as adding, getting and reserving rooms, getting a customer reservations
 * and all reservations.
 * <p>
 * This class is a singleton and should be accessed via {@link ReservationService#getInstance()}.
 */
final public class ReservationService {
    final private static ReservationService instance = new ReservationService();

    final private Collection<Reservation> reservations = new ArrayList<>();
    final private Collection<IRoom> rooms = new ArrayList<>();

    private ReservationService() {
        // private constructor
    }

    /**
     * Returns the singleton instance of {@code ReservationService}.
     *
     * @return the single instance of the service.
     */
    public static ReservationService getInstance() {
        return instance;
    }

    /**
     * Add a room.
     *
     * @param room the room.
     */
    public void addRoom(IRoom room) {
        Room newRoom = new Room(room.getNumber(), room.getPrice(), room.getType());
        // TODO
    }

    /**
     * Get a room.
     *
     * @param roomNumber the room number
     * @return the room.
     */
    public IRoom getARoom(String roomNumber) {
        return null; // TODO
    }

    /**
     * Reserve a room.
     *
     * @param customer the customer that is reserving the room.
     * @param room     the room that is being reserved.
     * @param checkIn  the check-in date for this reservation.
     * @param checkOut the check-out date for this reservation.
     * @return the reservation.
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        return new Reservation(customer, room, checkIn, checkOut);
    }

    /**
     * Get the reservations for a customer.
     *
     * @param customer the customer.
     * @return the customers reservations.
     */
    public Collection<Reservation> getCustomerReservations(Customer customer) {
        return null; // TODO
    }

    /**
     * Print all reservations.
     */
    public void printAllReservation() {
        System.out.println("TODO");
    }
}
