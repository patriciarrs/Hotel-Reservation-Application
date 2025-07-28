package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

/**
 * For the hotel staff only. Used for defining the API. Has little to no behavior. Makes use of the Service classes to
 * implement its methods.
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

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
