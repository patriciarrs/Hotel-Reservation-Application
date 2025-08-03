package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a customer of the hotel. Provides its properties.
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
     * @throws IllegalArgumentException if the e-mail format is invalid.
     */
    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;

        final String emailRegex = "^(.+)@(.+).(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        boolean isEmailValid = matcher.matches();

        if (!isEmailValid) {
            throw new IllegalArgumentException(
                    "The e-mail should look like 'name@domain.extension' (e.g., user@example.com).");
        }

        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "First name: " + firstName + "; Last name: " + lastName + "; E-mail: " + email + ".";
    }
}
