package utils;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailInput {
    private EmailInput() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Get the e-mail input .
     *
     * @param scanner the text scanner input.
     * @return the e-mail.
     * @throws NoSuchElementException if no line is found on the scanner.
     * @throws IllegalStateException  if the scanner is closed.
     */
    public static String getEmailInput(Scanner scanner)
            throws NoSuchElementException, IllegalStateException {
        System.out.println("Enter e-mail with format name@domain.com:");
        String input = scanner.nextLine();

        return getValidatedEmail(input);
    }

    /**
     * Validates the e-mail.
     *
     * @param email the e-mail to validate.
     * @return the validated e-mail.
     * @throws IllegalArgumentException if the e-mail format is invalid.
     */
    public static String getValidatedEmail(String email)
            throws IllegalArgumentException {
        final String emailRegex = "^(.+)@(.+).(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        boolean isEmailValid = matcher.matches();

        if (!isEmailValid) {
            throw new IllegalArgumentException(
                    "The e-mail should look like 'name@domain.extension' (e.g., user@example.com).");
        }

        return email;
    }
}