package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides reservation-related operations such as adding, getting and reserving rooms, getting a customer reservations
 * and all reservations.
 * <p>
 * This class is a singleton and should be accessed via {@link ReservationService#getInstance()}.
 */
final public class ReservationService {
    private static ReservationService instance;

    final private Map<String, List<Reservation>> roomNumberToReservations;
    final private Map<String, IRoom> roomNumberToRoom;

    private ReservationService() {
        roomNumberToReservations = new HashMap<>();
        roomNumberToRoom = new HashMap<>();
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

    /**
     * Add a room to the roomNumberToRoom map.
     *
     * @param room the room.
     */
    public void addRoom(IRoom room) {
        String number = room.getNumber();

        Room newRoom = new Room(number, room.getPrice(), room.getType());

        roomNumberToRoom.put(number, newRoom);
    }

    /**
     * Get a room from the roomNumberToRoom map.
     *
     * @param roomNumber the room number
     * @return the room.
     */
    public IRoom getRoom(String roomNumber) {
        return roomNumberToRoom.get(roomNumber);
    }

    /**
     * Get all rooms.
     *
     * @return all rooms.
     */
    public Collection<IRoom> getAllRooms() {
        return roomNumberToRoom.values();
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
    public Reservation reserveRoom(Customer customer, IRoom room, LocalDate checkIn, LocalDate checkOut) {


        return new Reservation(customer, room, checkIn, checkOut);
    }

    /**
     * Find the available rooms for the desired dates.
     *
     * @param checkIn  check-in date.
     * @param checkOut check-out date.
     * @return the available rooms for the desired dates.
     */
    public Collection<IRoom> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        Collection<IRoom> availableRooms = new ArrayList<>();

        for (List<Reservation> roomReservations : roomNumberToReservations.values()) {
            for (Reservation reservation : roomReservations) {
                boolean isBooked = isBooked(reservation.getCheckIn(), reservation.getCheckOut(), checkIn, checkOut);

                if (!isBooked) {
                    availableRooms.add(reservation.getRoom());
                }
            }
        }

        return availableRooms;
    }

    /**
     * Get the reservations for a customer.
     *
     * @param customer the customer.
     * @return the customer reservations.
     */
    public Collection<Reservation> getCustomerReservations(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (List<Reservation> roomReservation : roomNumberToReservations.values()) {
            for (Reservation reservation : roomReservation) {
                if (reservation.getCustomer().equals(customer)) {
                    customerReservations.add(reservation);
                }
            }
        }

        return customerReservations;
    }

    /**
     * Print all reservations.
     */
    public void printAllReservations() {
        System.out.println(roomNumberToReservations);
    }

    /**
     * Check if the room is already booked for the desired dates.
     *
     * @param checkIn1  the existing reservation check-in date.
     * @param checkOut1 the existing reservation check-out date.
     * @param checkIn2  the desired reservation check-in date.
     * @param checkOut2 the desired reservation check-out date.
     * @return true if the room is already booked for the desired dates.
     */
    private boolean isBooked(LocalDate checkIn1, LocalDate checkOut1, LocalDate checkIn2, LocalDate checkOut2) {
        return checkIn1.isBefore(checkOut2) && checkIn2.isBefore(checkOut1);
    }
}
