package model;

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
     * Creates a customer with the specified first and last name and email.
     *
     * @param firstName the customer first name.
     * @param lastName  the customer last name.
     * @param email     the customer email.
     * @throws IllegalArgumentException if the email is not valid.
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
                    "The email should look like 'name@domain.extension' (e.g., user@example.com).");
        }

        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
