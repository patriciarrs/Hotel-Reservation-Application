package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

/**
 * API to the UI intended for the hotel staff only.
 * <p>
 * Intermediary between the UI components and services. Should have little to no behavior and make use of the Service
 * classes to implement its methods.
 */
final public class AdminResource {
    private static AdminResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }

        return instance;
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

    /**
     * Add test data (rooms and customers) for testing.
     */
    public void addTestData() {
        reservationService.addTestData();
        customerService.addTestData();
    }
}
