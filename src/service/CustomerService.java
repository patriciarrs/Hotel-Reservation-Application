package service;

import model.Customer;

import java.util.Collection;

/**
 * Stateful service.
 */
public class CustomerService {
    private static final CustomerService instance = new CustomerService();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {

    }

    public Customer getCustomer(String customerEmail) {
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return java.util.List.of();
    }
}
