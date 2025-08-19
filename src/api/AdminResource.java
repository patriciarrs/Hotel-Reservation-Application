package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

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
    public List<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    /**
     * Get all customers.
     *
     * @return all customers.
     */
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Display all reservations.
     */
    public void displayAllReservations() {
        reservationService.printAllReservations();
    }

    /**
     * Add test data (rooms, customers and reservations).
     */
    public void addTestData() {
        customerService.addTestData();

        Customer customer1 = getCustomer("j@gmail.com");
        Customer customer2 = getCustomer("mike@email.com");

        Customer[] customers = {customer1, customer2};

        reservationService.addTestData(customers);
    }

    /**
     * Get a customer by e-mail.
     *
     * @param email the customer e-mail.
     * @return the customer.
     */
    private Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
}