package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides reservation-related operations such as adding, getting and reserving rooms, getting a customer reservations
 * and all reservations.
 * <p>
 * This class is a singleton and should be accessed via {@link ReservationService#getInstance()}.
 */
final public class ReservationService {
    private static ReservationService instance;

    final private Collection<Reservation> reservations;
    Map<String, IRoom> rooms;


    private ReservationService() {
        reservations = new ArrayList<>();
        rooms = new HashMap<>();
    }

    /**
     * Returns the singleton instance of {@code ReservationService}.
     *
     * @return the single instance of the service.
     */
    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    /**
     * Add a room.
     *
     * @param room the room.
     */
    public void addRoom(IRoom room) {
        String number = room.getNumber();

        Room newRoom = new Room(number, room.getPrice(), room.getType());

        rooms.put(number, newRoom);
    }

    /**
     * Get the room.
     *
     * @param roomNumber the room number
     * @return the room.
     */
    public IRoom getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    /**
     * Reserve the room.
     *
     * @param customer the customer that is reserving the room.
     * @param room     the room that is being reserved.
     * @param checkIn  the check-in date for this reservation.
     * @param checkOut the check-out date for this reservation.
     * @return the reservation.
     */
    public Reservation reserveRoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        return new Reservation(customer, room, checkIn, checkOut);
        // TODO
    }

    /**
     * Get the reservations for a customer.
     *
     * @param customer the customer.
     * @return the customers reservations.
     */
    public Collection<Reservation> getCustomerReservations(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }

        return customerReservations;
    }

    /**
     * Print all reservations.
     */
    public void printAllReservations() {
        System.out.println(reservations);
    }
}
