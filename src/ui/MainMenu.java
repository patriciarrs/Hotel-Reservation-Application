package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;

import static java.lang.Integer.parseInt;

/**
 * Main menu for the users who want to book a room.
 */
public class MainMenu {
    /**
     * Initialize the main menu UI.
     */
    public void getMainMenu() {
        String menuMessage = """
                Welcome to the Hotel Reservation Application
                
                _______________________________________________
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit
                _______________________________________________
                """;
        String selectOptionMessage = "Please select a number for the menu option.";

        System.out.println(menuMessage);
        System.out.println(selectOptionMessage);

        Scanner scanner = new Scanner(System.in);

        handleMenuOptionSelections(scanner, selectOptionMessage);
    }

    /**
     * Handle the menu option selections.
     *
     * @param scanner             the text scanner input.
     * @param selectOptionMessage the message for selecting an option.
     * @throws IllegalArgumentException if the selected option is not an integer, or does not exist.
     */
    private void handleMenuOptionSelections(Scanner scanner, String selectOptionMessage)
            throws IllegalArgumentException {
        boolean isInputValid;

        do {
            try {
                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput < 1 || intInput > 5) {
                    throw new IllegalArgumentException(selectOptionMessage);
                }

                isInputValid = true;

                switch (intInput) {
                    case 1 -> findAndReserveARoom(scanner);
                    case 2 -> System.out.println("2");
                    case 3 -> System.out.println("3");
                    case 4 -> System.out.println("4");
                    case 5 -> scanner.close();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(selectOptionMessage);
                isInputValid = false;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                isInputValid = false;
            }
        } while (!isInputValid);
    }

    /**
     * Find available rooms based on check-in and check-out dates and reserve a room.
     *
     * @param scanner the text scanner input.
     */
    private void findAndReserveARoom(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate checkInDate = getCheckInDate(scanner, formatter);
        LocalDate checkOutDate = getCheckOutDate(scanner, formatter, checkInDate);

        /*HotelResource hotelResource = HotelResource.getInstance();
        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(checkIn, checkOut);

        System.out.println(availableRooms);

        System.out.println("Would you like to book a room? y/n");
        String shouldReserveRoom = scanner.nextLine();

        switch (shouldReserveRoom) {
            case "y":
                reserveARoom();
                break;
            case "n":
                break;
            default:
                System.out.println("Please enter Y (Yes) or N (No).");
        }
*/
    }

    /**
     * Get the check-in date.
     *
     * @param scanner   the text scanner input.
     * @param formatter the date formatter.
     * @return the check-in date.
     */
    private LocalDate getCheckInDate(Scanner scanner, DateTimeFormatter formatter) {
        String inputMessage = "Enter check-in date as yyyy/MM/dd (e.g., 2026/01/01).";
        Predicate<LocalDate> inputValidation = date -> date.isAfter(LocalDate.now());
        String errorMessage = "Enter a check-in date in the future.";

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
    private LocalDate getCheckOutDate(Scanner scanner, DateTimeFormatter formatter, LocalDate checkInDate) {
        String inputMessage = "Enter check-out date as yyyy/MM/dd (e.g., 2026/01/15).";
        Predicate<LocalDate> inputValidation = date -> date.isAfter(checkInDate);
        String errorMessage = "Enter a check-out date that is after the check-in.";

        return getDate(scanner, formatter, inputMessage, inputValidation, errorMessage);
    }

    /**
     * TODO
     */
    private void reserveARoom() {
        System.out.println("Do you have and account with us? y/n");
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
     * @throws DateTimeParseException   if the input date format is invalid.
     * @throws IllegalArgumentException if the input date is invalid.
     */
    private LocalDate getDate(Scanner scanner, DateTimeFormatter formatter, String inputMessage,
                              Predicate<LocalDate> inputValidation, String errorMessage)
            throws DateTimeParseException, IllegalArgumentException {
        System.out.println(inputMessage);

        while (true) {
            try {
                String input = scanner.nextLine();
                LocalDate dateInput = LocalDate.parse(input, formatter);

                if (!inputValidation.test(dateInput)) {
                    throw new IllegalArgumentException(errorMessage);
                }

                return dateInput;
            } catch (DateTimeParseException e) {
                System.out.println(inputMessage);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
