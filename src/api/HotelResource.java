package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;


/**
 * Singleton resource responsible for loading and providing hotel settings (for public usage).
 * <p>
 * Used for defining the API. Has little to no behavior. Makes use of the Service classes to implement its methods.
 * <p>
 * Loads configuration from a predefined source (e.g., a service) at startup and provides read-only access throughout
 * the application lifecycle.
 * </p>
 * This class is a singleton and should be accessed via {@link #getInstance()}.
 */
final public class HotelResource {
    private static HotelResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private HotelResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    /**
     * Returns the singleton instance of {@code HotelResource}.
     *
     * @return the global hotel manager.
     */
    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
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
     * @param email        the customer e-mail.
     * @param room         the room.
     * @param checkInDate  the check-in date.
     * @param checkOutDate the check-out date.
     * @return the reservation.
     */
    public Reservation reserveRoom(String email, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Customer customer = getCustomer(email);

        return reservationService.reserveRoom(customer, room, checkInDate, checkOutDate);
    }

    /**
     * Get the customer reservations (based on his e-mail).
     *
     * @param email the customer e-mail.
     * @return the customer reservations.
     */
    public Collection<Reservation> getCustomersReservations(String email) {
        Customer customer = getCustomer(email);

        return reservationService.getCustomerReservations(customer);
    }

    /**
     * Find the available rooms for reservation given the check-in and check-out dates.
     *
     * @param checkIn  the check-in date.
     * @param checkOut the check-out date.
     * @return the available rooms.
     */
    public Collection<IRoom> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return reservationService.findAvailableRooms(checkIn, checkOut);
    }
}
