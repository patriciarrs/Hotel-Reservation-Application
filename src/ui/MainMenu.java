package ui;

import api.HotelResource;
import model.Customer;
import model.Dates;
import model.IRoom;
import model.Reservation;
import utils.DatesInput;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static utils.EmailInput.getEmailInput;
import static utils.StringInput.getNoCustomValidationStringInput;
import static utils.StringInput.getYesOrNo;

/**
 * Main menu for the users who want to book a room.
 */
final public class MainMenu {
    final HotelResource hotelResource = HotelResource.getInstance();

    /**
     * Initialize the main menu UI.
     */
    public void getMainMenu() {
        System.out.println("""
                Welcome to the Hotel Reservation Application
                
                _______________________________________________
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit
                _______________________________________________
                """);

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
        String errorMessage = "Only numbers between 1 and 5 are allowed.";
        Scanner scanner = new Scanner(System.in);

        boolean isInputValid;

        do {
            try {
                System.out.println("Please select a number for the menu option:");

                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput < 1 || intInput > 5) {
                    throw new IllegalArgumentException(errorMessage);
                }

                isInputValid = true;

                switch (intInput) {
                    case 1 -> findAndReserveARoom(scanner);
                    case 2 -> seeMyReservations(scanner);
                    case 3 -> createAnAccount(scanner);
                    case 4 -> getAdminMenu(scanner);
                    case 5 -> scanner.close();
                }

                getMainMenu();
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
                isInputValid = false;
            } catch (Exception e) {
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
        Dates dates = DatesInput.getDates(scanner);

        String roomSearchType = getRoomSearchType(scanner);

        List<IRoom> availableRooms = hotelResource.findAvailableRooms(dates, roomSearchType);

        if (availableRooms.isEmpty()) {
            dates = getAlternativeDates(dates, scanner);
            availableRooms = hotelResource.findAvailableRooms(dates, roomSearchType);
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected dates and search type.");
            return;
        }

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        boolean isBooking = getYesOrNo("Would you like to book a room?", scanner);

        if (isBooking) {
            boolean hasAccountAccordingToUser = getYesOrNo("Do you have an account with us?", scanner);

            String email;
            if (hasAccountAccordingToUser) {
                email = getExistingEmailInput(scanner);
            } else {
                email = createAnAccount(scanner);
            }

            reserveRoom(email, scanner, availableRooms, dates);
        }
    }

    /**
     * Main Menu Option 2: See the customer reservations (based on his e-mail).
     *
     * @param scanner the text scanner input.
     */
    private void seeMyReservations(Scanner scanner) {
        String email = getExistingEmailInput(scanner);

        List<Reservation> reservations = hotelResource.getCustomersReservations(email);

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    /**
     * Main Menu Option 3: Create a customer account.
     *
     * @param scanner the text scanner input.
     */
    private String createAnAccount(Scanner scanner) {

        do {
            try {
                String email = getNewEmailInput(scanner);
                String firstName = getNoCustomValidationStringInput("Enter first name:", scanner);
                String lastName = getNoCustomValidationStringInput("Enter last name:", scanner);

                hotelResource.createCustomer(email, firstName, lastName);

                return email;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Main Menu Option 4: Get the admin menu UI.
     *
     * @param scanner the text scanner input.
     */
    private void getAdminMenu(Scanner scanner) {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.getAdminMenu(scanner);
    }

    /**
     * Get the room search type - A (all rooms), P (only paid room) or F (only free rooms).
     *
     * @param scanner the text scanner input.
     * @return the room search type - A (all rooms), P (only paid room) or F (only free rooms).
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws IllegalArgumentException if the input is not A, P or F (case-insensitive).
     */
    private String getRoomSearchType(Scanner scanner)
            throws NoSuchElementException, IllegalStateException, IllegalArgumentException {
        System.out.println("Would you like to search for all rooms (a), only paid rooms (p) or only free rooms (f)?");

        do {
            try {
                String input = scanner.nextLine();

                if (!input.equalsIgnoreCase("a") && !input.equalsIgnoreCase("p") && !input.equalsIgnoreCase("f")) {
                    throw new IllegalArgumentException(
                            "Please enter A (all rooms), P (only paid room) or F (only free rooms):");
                }

                return input.toUpperCase();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Get the alternative check-in and check-out dates chosen by the user.
     *
     * @param dates   the original desired check-in and check-out dates.
     * @param scanner the text scanner input.
     * @return the alternative dates.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws NumberFormatException    if the input is not a parsable integer.
     * @throws IllegalArgumentException if the selected option not a positive or negative integer.
     */
    private Dates getAlternativeDates(Dates dates, Scanner scanner)
            throws NoSuchElementException, IllegalStateException, NumberFormatException, IllegalArgumentException {
        System.out.println("No rooms available for the selected dates.");
        System.out.println(
                "Enter how many days out the room recommendation should search: (e.g., 7 to search 7 days later or -7 to search 7 days earlier)");

        do {
            try {
                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput == 0) {
                    throw new IllegalArgumentException("Only positive or negative integers are allowed.");
                }

                LocalDate alternativeCheckIn = dates.checkIn().plusDays(intInput);
                LocalDate alternativeCheckOut = dates.checkOut().plusDays(intInput);

                return new Dates(alternativeCheckIn, alternativeCheckOut);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Get an existing user e-mail input.
     *
     * @param scanner the text scanner input.
     * @return the user e-mail.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws IllegalArgumentException if the e-mail format is invalid or already in use.
     */
    private String getExistingEmailInput(Scanner scanner)
            throws NoSuchElementException, IllegalStateException, IllegalArgumentException {
        do {
            try {
                return getEmailInput(scanner);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Get a new user e-mail input.
     *
     * @param scanner the text scanner input.
     * @return the user e-mail.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws IllegalArgumentException if the e-mail format is invalid or already in use.
     */
    private String getNewEmailInput(Scanner scanner)
            throws NoSuchElementException, IllegalStateException, IllegalArgumentException {
        do {
            try {
                String emailInput = getEmailInput(scanner);

                List<Customer> customers = hotelResource.getAllCustomers();

                if (customers.stream().anyMatch(customer -> emailInput.equals(customer.getEmail()))) {
                    throw new IllegalArgumentException("That customer e-mail is already in use.");
                }

                return emailInput;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Reserve a room from the available rooms.
     *
     * @param email          the customer e-mail.
     * @param scanner        the text scanner input.
     * @param availableRooms the available rooms for the desired dates.
     * @param dates          the desired check-in and check-out dates.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws IllegalArgumentException if the input is not a free room.
     */
    private void reserveRoom(String email, Scanner scanner, List<IRoom> availableRooms, Dates dates) {
        boolean hasAccountAccordingToSystem = checkHasAccountAccordingToSystem(email);

        if (hasAccountAccordingToSystem) {
            boolean isValidRoom = false;

            do {
                try {
                    System.out.println("What room number would you like to reserve?");
                    String input = scanner.nextLine();

                    isValidRoom = availableRooms.stream().anyMatch(room -> room.getNumber().equals(input));

                    if (!isValidRoom) {
                        throw new IllegalArgumentException("Only the room numbers displayed above are allowed.");
                    }

                    IRoom room = hotelResource.getRoom(input);

                    hotelResource.reserveRoom(email, room, dates);

                    List<Reservation> reservations = hotelResource.getCustomersReservations(email);

                    for (Reservation reservation : reservations) {
                        System.out.println(reservation);
                    }
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            } while (!isValidRoom);
        } else {
            System.out.println(
                    "Account not found. Please create an account with us to conclude booking.");

            String newAccountEmail = createAnAccount(scanner);

            reserveRoom(newAccountEmail, scanner, availableRooms, dates);
        }
    }

    /**
     * Check if the user has an account in the system.
     *
     * @param email the user e-mail.
     * @return true if the user has an account in the system.
     */
    private boolean checkHasAccountAccordingToSystem(String email) {
        Customer customer = hotelResource.getCustomer(email);

        return customer != null;
    }
}