package ui;

import api.HotelResource;
import model.Customer;
import model.Dates;
import model.IRoom;
import utils.DatesInput;
import utils.YesOrNoInput;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

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
        Dates dates = DatesInput.getDates(scanner);

        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(dates.checkIn(), dates.checkOut());

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        boolean isBooking = YesOrNoInput.getYesOrNo("Would you like to book a room?", scanner);

        if (isBooking) {
            // TODO refactor
            boolean hasAccountAccordingToUser = YesOrNoInput.getYesOrNo("Do you have an account with us?", scanner);

            if (hasAccountAccordingToUser) {
                String email = getEmail(scanner);
                reserveRoom(email, scanner, availableRooms, dates);
            } else {
                createAnAccount(scanner);
            }
        } else {
            // TODO What happens if the user doesn't want to book?
        }
    }

    /**
     * Reserve a room from the available rooms.
     *
     * @param email          the customer email.
     * @param scanner        the text scanner input.
     * @param availableRooms the available rooms for the desired dates.
     * @param dates          the desired check-in and check-out dates.
     */
    private void reserveRoom(String email, Scanner scanner, Collection<IRoom> availableRooms, Dates dates) {
        boolean hasAccountAccordingToSystem = hasAccountAccordingToSystem(email, scanner);

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

                    for (IRoom room : availableRooms) {
                        if (room.getNumber().equals(input)) {
                            hotelResource.reserveRoom(email, room, dates.checkIn(), dates.checkOut());
                        }
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
     * @param email   the user e-mail.
     * @param scanner the text scanner input.
     * @return true if the user has an account in the system.
     */
    private boolean hasAccountAccordingToSystem(String email, Scanner scanner) {
        Customer customer = hotelResource.getCustomer(email);

        return customer != null;
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
                String firstName = getFirstName(scanner);
                String lastName = getLastName(scanner);

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
