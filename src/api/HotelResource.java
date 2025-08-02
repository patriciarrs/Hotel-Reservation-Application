package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;


/**
 * Singleton resource responsible for loading and providing hotel configuration settings (for public usage).
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
     * @param email the customer email.
     * @return the customer.
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getRoom(roomNumber);
    }

    public Reservation reserveRoom(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Customer customer = getCustomer(customerEmail);

        return reservationService.reserveRoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);

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
