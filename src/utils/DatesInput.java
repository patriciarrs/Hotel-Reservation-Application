package utils;

import model.Dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;

public final class DatesInput {
    private DatesInput() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Get the desired check-in and check-out dates.
     *
     * @param scanner the text scanner input.
     * @return the desired check-in and check-out dates.
     */
    public static Dates getDates(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate checkIn = getCheckIn(scanner, formatter);
        LocalDate checkOut = getCheckOut(scanner, formatter, checkIn);

        return new Dates(checkIn, checkOut);
    }

    /**
     * Get the check-in date.
     *
     * @param scanner   the text scanner input.
     * @param formatter the date formatter.
     * @return the check-in date.
     */
    private static LocalDate getCheckIn(Scanner scanner, DateTimeFormatter formatter) {
        String inputMessage = "Enter check-in date as yyyy/MM/dd (e.g., 2026/01/01):";
        Predicate<LocalDate> inputValidation = date -> date.isAfter(LocalDate.now());
        String errorMessage = "Enter a check-in date in the future:";

        return getDate(scanner, formatter, inputMessage, inputValidation, errorMessage);
    }

    /**
     * Get the check-out date.
     *
     * @param scanner     the text scanner input.
     * @param formatter   the date formatter.
     * @param checkInDate the check-in date.
     * @return the check-out date.
     */
    private static LocalDate getCheckOut(Scanner scanner, DateTimeFormatter formatter, LocalDate checkInDate) {
        String inputMessage = "Enter check-out date as yyyy/MM/dd (e.g., 2026/01/15):";
        Predicate<LocalDate> inputValidation = date -> date.isAfter(checkInDate);
        String errorMessage = "Enter a check-out date that is after the check-in:";

        return getDate(scanner, formatter, inputMessage, inputValidation, errorMessage);
    }

    /**
     * Get a valid date.
     *
     * @param scanner         the text scanner input.
     * @param formatter       the date formatter.
     * @param inputMessage    the message that asks for the user input.
     * @param inputValidation single argument function that validates the input.
     * @param errorMessage    message to show if the input is invalid.
     * @return the valid input date.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws DateTimeParseException   if the input date format is invalid.
     * @throws IllegalArgumentException if the input date is invalid.
     */
    private static LocalDate getDate(Scanner scanner, DateTimeFormatter formatter, String inputMessage,
                                     Predicate<LocalDate> inputValidation, String errorMessage)
            throws NoSuchElementException, IllegalStateException, DateTimeParseException, IllegalArgumentException {
        do {
            try {
                System.out.println(inputMessage);
                String input = scanner.nextLine();
                LocalDate dateInput = LocalDate.parse(input, formatter);

                if (!inputValidation.test(dateInput)) {
                    throw new IllegalArgumentException(errorMessage);
                }

                return dateInput;
            } catch (DateTimeParseException e) {
                System.out.println("Only the the format yyyy/MM/dd is allowed.");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }
}
