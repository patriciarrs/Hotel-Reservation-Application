package ui;

import api.HotelResource;
import model.Customer;
import model.Dates;
import model.IRoom;
import model.Reservation;
import utils.DatesInput;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static utils.StringInput.getEmail;
import static utils.StringInput.getNoCustomValidationStringInput;
import static utils.StringInput.getYesOrNo;

/**
 * Main menu for the users who want to book a room.
 */
final public class MainMenu {
    HotelResource hotelResource = HotelResource.getInstance();

    /**
     * Initialize the main menu UI.
     */
    public void getMenu() {
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
                        seeMyReservations(scanner);
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
        Dates dates = DatesInput.getDates(scanner);

        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(dates.checkIn(), dates.checkOut());

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        boolean isBooking = getYesOrNo("Would you like to book a room?", scanner);

        if (isBooking) {
            boolean hasAccountAccordingToUser = getYesOrNo("Do you have an account with us?", scanner);

            if (hasAccountAccordingToUser) {
                String email = getEmail(scanner);
                reserveRoom(email, scanner, availableRooms, dates);

                getAdminMenu(scanner);
            } else {
                createAnAccount(scanner);
            }
        } else {
            // TODO What happens if the user doesn't want to book?
        }
    }

    /**
     * Main Menu Option 2: See the customer reservations (based on his e-mail).
     *
     * @param scanner the text scanner input.
     */
    private void seeMyReservations(Scanner scanner) {
        String email = getEmail(scanner);

        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);

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
                String email = getEmail(scanner);
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
        adminMenu.getMenu(scanner);
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
    private void reserveRoom(String email, Scanner scanner, Collection<IRoom> availableRooms, Dates dates) {
        boolean hasAccountAccordingToSystem = hasAccountAccordingToSystem(email);

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

                    Reservation reservation = hotelResource.reserveRoom(email, room, dates.checkIn(), dates.checkOut());

                    System.out.println(reservation);
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
    private boolean hasAccountAccordingToSystem(String email) {
        Customer customer = hotelResource.getCustomer(email);

        return customer != null;
    }
}