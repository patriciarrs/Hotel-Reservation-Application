package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

/**
 * Singleton resource responsible for loading and providing admin settings (for the hotel staff only).
 * <p>
 * Used for defining the API. Has little to no behavior. Makes use of the Service classes to implement its methods.
 * <p>
 * Loads configuration from a predefined source (e.g., a service) at startup and provides read-only access throughout
 * the application lifecycle.
 * </p>
 * This class is a singleton and should be accessed via {@link #getInstance()}.
 */
final public class AdminResource {
    private static AdminResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    /**
     * Returns the singleton instance of {@code AdminResource}.
     *
     * @return the global admin manager.
     */
    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }

        return instance;
    }

    /**
     * Get a customer by e-mail.
     *
     * @param email the customer e-mail.
     * @return the customer.
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    /**
     * Add a room.
     *
     * @param room the room.
     */
    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    /**
     * Get all rooms.
     *
     * @return all rooms.
     */
    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
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
     * Display all reservations.
     */
    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
