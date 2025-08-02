package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides customer-related operations such as adding and getting them.
 * <p>
 * This class is a singleton and should be accessed via {@link CustomerService#getInstance()}.
 */
final public class CustomerService {
    private static CustomerService instance;

    Map<String, Customer> emailToCustomer;

    private CustomerService() {
        emailToCustomer = new HashMap<>();
    }

    /**
     * Returns the singleton instance of {@code CustomerService}.
     *
     * @return the single instance of the service.
     */
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }

        return instance;
    }

    /**
     * Add a customer.
     *
     * @param email     the customer email.
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
     * @param email the customer email.
     * @return a customer.
     */
    public Customer getCustomer(String email) {
        return emailToCustomer.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return emailToCustomer.values();
    }
}
