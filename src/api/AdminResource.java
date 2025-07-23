package api;

import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.List;

/**
 * For the hotel staff only. Used for defining the API. Has little to no behavior. Makes use of the Service classes to
 * implement its methods.
 */
final public class AdminResource {
    final private static AdminResource instance = new AdminResource();

    private AdminResource() {

    }

    public AdminResource getInstance() {
        return instance;
    }

    public Customer getCustomer(String email) {
        return null;
    }

    public void addRoom(List<IRoom> rooms) {

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
