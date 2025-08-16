package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Communicates with the resources to build the business logic necessary to provide feedback to the UI.
 * <p>
 * Stateful service (remembers things for the project) that uses Collections to manage information. As such, there's
 * only one of each service (Singleton).
 */
final public class CustomerService {
    private static CustomerService instance;

    private final Map<String, Customer> emailToCustomer;

    private CustomerService() {
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
     * Add customers test data.
     */
    public void addTestData() {
        addCustomer("j@gmail.com", "Jeff", "Philips");
        addCustomer("mike@email.com", "Mike", "Philips");
        addCustomer("shaun@email.com", "Shaun", "Philips");
        addCustomer("sally@email.com", "Sally", "Philips");
        addCustomer("cesar@email.com", "Cesar", "Philips");

        System.out.println("Customers: " + emailToCustomer);
    }
}
