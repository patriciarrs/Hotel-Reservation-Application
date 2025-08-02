package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;


record Dates(LocalDate checkIn, LocalDate checkOut) {
}

/**
 * Main menu for the users who want to book a room.
 */
final public class MainMenu {
    HotelResource hotelResource = HotelResource.getInstance();

    /**
     * Initialize the main menu UI.
     */
    public void getMenu() {
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

        System.out.println(menuMessage);

        handleMenuOptionSelections();
    }

    /**
     * Handle the menu option selections.
     *
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws NumberFormatException    if the selected option does not contain a parsable integer.
     * @throws IllegalArgumentException if the selected option is not an integer between 1 and 6.
     */
    private void handleMenuOptionSelections()
            throws NoSuchElementException, IllegalStateException, NumberFormatException, IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);

        boolean isInputValid;

        do {
            try {
                System.out.println("Please select a number for the menu option:");

                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput < 1 || intInput > 5) {
                    throw new IllegalArgumentException("Only numbers between 1 and 5 are allowed.");
                }

                isInputValid = true;

                switch (intInput) {
                    case 1:
                        findAndReserveARoom(scanner);
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        createAnAccount(scanner);
                        break;
                    case 4:
                        getAdminMenu(scanner);
                        break;
                    case 5:
                        scanner.close();
                        break;
                }

                getMenu();
            } catch (Exception e) {
                // TODO confirm what message is shown when IllegalArgumentException
                System.out.println(e.getLocalizedMessage());
                isInputValid = false;
            }
        } while (!isInputValid);
    }

    /**
     * Main Menu Option 1: Find available rooms based on check-in and check-out dates and reserve a room.
     *
     * @param scanner the text scanner input.
     */
    private void findAndReserveARoom(Scanner scanner) {
        Dates dates = getDates(scanner);

        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(dates.checkIn(), dates.checkOut());

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        boolean isBooking = checkIsBooking(scanner);

        if (isBooking) {
            // TODO refactor
            boolean hasAccountAccordingToUser = hasAccountAccordingToUser(scanner);

            if (hasAccountAccordingToUser) {
                String email = getEmail(scanner);
                boolean hasAccountAccordingToSystem = hasAccountAccordingToSystem(email, scanner);

                if (hasAccountAccordingToSystem) {
                    System.out.println("What room number would you like to reserve?");
                    String input = scanner.nextLine();

                    boolean isValidRoom = availableRooms.stream().anyMatch(room -> room.getNumber().equals(input));

                    if (isValidRoom) {
                        for (IRoom room : availableRooms) {
                            if (room.getNumber().equals(input)) {
                                hotelResource.reserveRoom(email, room, dates.checkIn(), dates.checkOut());
                            }
                        }
                    } else {
                        // TODO error
                    }

                } else {
                    System.out.println(
                            "Account not found. Please create an account with us to conclude booking.");
                    createAnAccount(scanner);
                }
            } else {
                createAnAccount(scanner);
            }
        } else {
            // TODO What happens if the user doesn't want to book?
        }
    }

    /**
     * Get the desired check-in and check-out dates.
     *
     * @param scanner the text scanner input.
     * @return the desired check-in and check-out dates.
     */
    private Dates getDates(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate checkIn = DateUtils.getCheckIn(scanner, formatter);
        LocalDate checkOut = DateUtils.getCheckOut(scanner, formatter, checkIn);

        return new Dates(checkIn, checkOut);
    }

    /**
     * Check if the user has an account in the system.
     *
     * @param email   the user e-mail.
     * @param scanner the text scanner input.
     * @return true if the user has an account in the system.
     */
    private boolean hasAccountAccordingToSystem(String email, Scanner scanner) {
        Customer customer = hotelResource.getCustomer(email);

        return customer != null;
    }

    /**
     * Check if the user wants to book a room.
     *
     * @param scanner the text scanner input.
     * @return true if the user wants to book a room.
     * @throws IllegalArgumentException if the input is invalid.
     */
    private boolean checkIsBooking(Scanner scanner) throws IllegalArgumentException {
        System.out.println("Would you like to book a room? y/n");

        do {
            try {
                String input = scanner.nextLine();

                if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    throw new IllegalArgumentException("Please enter Y (Yes) or N (No):");
                }

                return input.equalsIgnoreCase("y");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Ask the user if he already has an existing account.
     *
     * @param scanner the text scanner input.
     * @return true if the user already has an existing account.
     * @throws IllegalArgumentException if the input is invalid.
     */
    private boolean hasAccountAccordingToUser(Scanner scanner) throws IllegalArgumentException {
        System.out.println("Do you have an account with us? y/n");

        do {
            try {
                String input = scanner.nextLine();

                if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    throw new IllegalArgumentException("Please enter Y (Yes) or N (No):");
                }

                return input.equalsIgnoreCase("y");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }


    /**
     * Main Menu Option 3: Create a customer account.
     *
     * @param scanner the text scanner input.
     */
    private void createAnAccount(Scanner scanner) {
        boolean isInputValid;

        do {
            try {
                String email = getEmail(scanner);
                String firstName = getFirstName(scanner);
                String lastName = getLastName(scanner);

                hotelResource.createCustomer(email, firstName, lastName);
                isInputValid = true;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                isInputValid = false;
            }
        } while (!isInputValid);
    }

    /**
     * Main Menu Option 4: Get the admin menu UI.
     *
     * @param scanner the text scanner input.
     */
    private void getAdminMenu(Scanner scanner) {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.getMenu(scanner);
    }

    /**
     * TODO
     */
    private void reserveARoom() {
        System.out.println("Do you have and account with us? y/n");
    }

    private String getEmail(Scanner scanner) {
        do {
            try {
                System.out.println("Enter e-mail with format name@domain.com:");
                String input = scanner.nextLine();

                final String emailRegex = "^(.+)@(.+).(.+)$";
                final Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(input);
                boolean isEmailValid = matcher.matches();

                if (!isEmailValid) {
                    throw new IllegalArgumentException(
                            "The email should look like 'name@domain.extension' (e.g., user@example.com).");
                }

                return input;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    private String getFirstName(Scanner scanner) {
        do {
            try {
                System.out.println("Enter first name:");

                return scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    private String getLastName(Scanner scanner) {
        do {
            try {
                System.out.println("Enter last name");

                return scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

}
