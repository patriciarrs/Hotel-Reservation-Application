package service;

import model.Customer;
import model.Dates;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Communicates with the resources, and other services, to build the business logic necessary to provide feedback to the
 * UI.
 * <p>
 * Stateful service (remembers things for the project) that uses Collections to manage information. As such, there's
 * only one of each service (Singleton).
 */
final public class ReservationService {
    private static ReservationService instance;

    private final CustomerService customerService;

    final private Map<String, List<Reservation>> roomNumberToReservations;
    final private Map<String, IRoom> roomNumberToRoom;

    private ReservationService() {
        customerService = CustomerService.getInstance();

        roomNumberToReservations = new HashMap<>();
        roomNumberToRoom = new HashMap<>();
    }

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
        roomNumberToRoom.put(room.getNumber(), room);
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
     * Reserve a room.
     *
     * @param customer the customer that is reserving the room.
     * @param room     the room that is being reserved.
     * @param dates    the check-in and check-out dates for this reservation.
     */
    public void reserveRoom(Customer customer, IRoom room, Dates dates) {
        new Reservation(customer, room, dates.checkIn(), dates.checkOut());
    }

    /**
     * Find the available rooms for the desired dates.
     *
     * @param dates the check-in and check-out dates for this reservation.
     * @return the available rooms for the desired dates.
     */
    public Collection<IRoom> findAvailableRooms(Dates dates) {
        Collection<IRoom> availableRooms = new ArrayList<>();

        for (List<Reservation> roomReservations : roomNumberToReservations.values()) {
            for (Reservation reservation : roomReservations) {
                boolean isBooked = isBooked(reservation.getCheckIn(), reservation.getCheckOut(), dates.checkIn(),
                        dates.checkOut());

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
     * Get all rooms.
     *
     * @return all rooms.
     */
    Collection<IRoom> getAllRooms() {
        return roomNumberToRoom.values();
    }

    /**
     * Create a customer.
     *
     * @param email     the customer e-mail.
     * @param firstName the customer first name.
     * @param lastName  the customer last name.
     */
    public void createCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    /**
     * Get a customer.
     *
     * @param email the customer e-mail.
     * @return a customer.
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    /**
     * Get all customers.
     *
     * @return all customers.
     */
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
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
