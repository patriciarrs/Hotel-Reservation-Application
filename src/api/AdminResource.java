package api;

import model.Customer;
import model.IRoom;

import java.util.Collection;

/**
 * For the hotel staff only. Used for defining the API. Has little to no behavior. Makes use of the Service classes to
 * implement its methods.
 */
final public class AdminResource {
    private static AdminResource instance;

    private AdminResource() {

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
        return null;
    }

    public void addRoom(IRoom room) {

    }

    public Collection<IRoom> getAllRooms() {
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return null;
    }

    public void displayAllReservations() {

    }
}
