package api;

import model.Customer;
import model.Dates;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

/**
 * API to the UI intended for public usage.
 * <p>
 * Intermediary between the UI components and services. Should have little to no behavior and make use of the Service
 * classes to implement its methods.
 */
final public class HotelResource {
    private static HotelResource instance;

    private final ReservationService reservationService;
    private final CustomerService customerService;

    private HotelResource() {
        reservationService = ReservationService.getInstance();
        customerService = CustomerService.getInstance();
    }

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
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
     * Get a room by room number.
     *
     * @param number the room number
     * @return the room.
     */
    public IRoom getRoom(String number) {
        return reservationService.getRoom(number);
    }

    /**
     * Reserve a room.
     *
     * @param email the customer e-mail.
     * @param room  the room.
     * @param dates the check-in and check-out dates for this reservation.
     */
    public void reserveRoom(String email, IRoom room, Dates dates) {
        Customer customer = getCustomer(email);

        reservationService.reserveRoom(customer, room, dates);
    }

    /**
     * Get a customer.
     *
     * @param email the customer e-mail.
     * @return the customer.
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    /**
     * Get the customer reservations (based on his e-mail).
     *
     * @param email the customer e-mail.
     * @return the customer reservations.
     */
    public List<Reservation> getCustomersReservations(String email) {
        Customer customer = getCustomer(email);

        return reservationService.getCustomerReservations(customer);
    }

    /**
     * Find the available rooms for reservation given the check-in and check-out dates.
     *
     * @param dates          the check-in and check-out dates for this reservation.
     * @param roomSearchType the room search type - A (all rooms), P (only paid room) or F (only free rooms).
     * @return the available rooms.
     */
    public List<IRoom> findAvailableRooms(Dates dates, String roomSearchType) {
        return reservationService.findAvailableRooms(dates, roomSearchType);
    }

    /**
     * Get all rooms.
     *
     * @return all rooms.
     */
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
