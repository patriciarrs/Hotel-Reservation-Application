package model;

import java.util.Objects;

import static utils.EmailInput.getValidatedEmail;

/**
 * Represents the domain of a customer. Models the data object for the hotel reservation application domain.
 */
final public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    /**
     * Creates a customer with the specified first and last name and e-mail.
     *
     * @param firstName the customer first name.
     * @param lastName  the customer last name.
     * @param email     the customer e-mail.
     */
    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = getValidatedEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public String toString() {
        return "First name: " + firstName + "; Last name: " + lastName + "; E-mail: " + email + ".";
    }
}
