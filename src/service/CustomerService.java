package service;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Communicates with the resources, and other services, to build the business logic necessary to provide feedback to the
 * UI.
 * <p>
 * Stateful service (remembers things for the project) that uses Collections to manage information. As such, there's
 * only one of each service (Singleton).
 */
final public class CustomerService {
    private static CustomerService instance;
    private final ReservationService reservationService;

    private final Map<String, Customer> emailToCustomer;

    private CustomerService() {
        reservationService = ReservationService.getInstance();

        emailToCustomer = new HashMap<>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }

        return instance;
    }

    /**
     * Add a customer.
     *
     * @param email     the customer e-mail.
     * @param firstName the customer first name.
     * @param lastName  the customer last name.
     */
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);

        emailToCustomer.put(email, customer);
    }

    /**
     * Get a customer.
     *
     * @param email the customer e-mail.
     * @return a customer.
     */
    public Customer getCustomer(String email) {
        return emailToCustomer.get(email);
    }

    /**
     * Get all customers.
     *
     * @return all customers.
     */
    public Collection<Customer> getAllCustomers() {
        return emailToCustomer.values();
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
     * Print all reservations.
     */
    public void printAllReservations() {
        reservationService.printAllReservations();
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
     * Add test data (rooms and customers) for testing.
     */
    public void addTestData() {
        addRoom(new Room("100", 120.0, RoomType.SINGLE));
        addRoom(new Room("101", 130.0, RoomType.DOUBLE));
        addRoom(new Room("102", 125.0, RoomType.SINGLE));
        addRoom(new Room("103", 100.0, RoomType.DOUBLE));
        addRoom(new Room("104", 145.0, RoomType.DOUBLE));
        addRoom(new Room("105", 100.0, RoomType.SINGLE));
        addRoom(new Room("106", 250.0, RoomType.DOUBLE));
        addRoom(new Room("107", 0.0, RoomType.SINGLE));

        addCustomer("j@gmail.com", "Jeff", "Philips");
        addCustomer("mike@email.com", "Mike", "Philips");
        addCustomer("shaun@email.com", "Shaun", "Philips");
        addCustomer("sally@email.com", "Sally", "Philips");
        addCustomer("cesar@email.com", "Cesar", "Philips");
    }
}
