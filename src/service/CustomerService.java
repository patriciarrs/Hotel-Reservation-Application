package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides customer-related operations such as adding and getting them.
 * <p>
 * This class is a singleton and should be accessed via {@link CustomerService#getInstance()}.
 */
final public class CustomerService {
    final private static CustomerService instance = new CustomerService();

    final private Collection<Customer> customers = new ArrayList<>();

    private CustomerService() {
        // private constructor
    }

    /**
     * Returns the singleton instance of {@code CustomerService}.
     *
     * @return the single instance of the service.
     */
    public static CustomerService getInstance() {
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
        // TODO
    }

    /**
     * Get a customer.
     *
     * @param email the customer email.
     * @return a customer.
     */
    public Customer getCustomer(String email) {
        return null; // TODO
    }

    public Collection<Customer> getAllCustomers() {
        return java.util.List.of(); // TODO
    }
}
