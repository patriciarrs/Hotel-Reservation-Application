package ui;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Admin menu for administrative functions.
 */
final public class AdminMenu {
    /**
     * Initialize the admin menu UI.
     */
    public void getMenu(Scanner scanner, String selectOptionMessage) {
        String menuMessage = """
                Admin Menu
                _______________________________________________
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                _______________________________________________
                """;

        System.out.println(menuMessage);
        System.out.println(selectOptionMessage);

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
                    case 1 -> System.out.println("1");
                    case 2 -> System.out.println("2");
                    case 3 -> System.out.println("3");
                    case 4 -> addARoom(scanner);
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
     * Add a new room.
     *
     * @param scanner the text scanner input.
     */
    private void addARoom(Scanner scanner) {
        boolean isAddingRoom;

        do {
            String roomNumber = getRoomNumber(scanner);
            double price = getPrice(scanner);
            int roomType = getRoomType(scanner);

            // TODO add room to collection

            isAddingRoom = checkIsAddingRoom(scanner);
        } while (isAddingRoom);
    }

    /**
     * Get the room number.
     *
     * @param scanner the text scanner input.
     * @return the room number.
     */
    private String getRoomNumber(Scanner scanner) {
        System.out.println("Enter room number:");

        // TODO check if room already exists
        return scanner.nextLine();
    }

    /**
     * Get the room price per night.
     *
     * @param scanner the text scanner input.
     * @return the room price per night.
     * @throws NullPointerException  if the price input is null.
     * @throws NumberFormatException if the price input is not a parsable double.
     */
    private double getPrice(Scanner scanner) throws NullPointerException, NumberFormatException {
        String message = "Enter price per night:";
        System.out.println(message);

        do {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println(message);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Get the room type.
     *
     * @param scanner the text scanner input.
     * @return the room type.
     * @throws IllegalArgumentException if the selected option in not 1 or 2.
     */
    private int getRoomType(Scanner scanner) throws IllegalArgumentException {
        String message = "Enter room type (1 for single bed, 2 for double bed):";
        System.out.println(message);

        do {
            try {
                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput != 1 && intInput != 2) {
                    throw new IllegalArgumentException(message);
                }

                return intInput;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Check if the admin user wants to add another room to the collection.
     *
     * @param scanner the text scanner input.
     * @return true if the admin user wants to add another room to the collection.
     * @throws IllegalArgumentException if the input is invalid.
     */
    private boolean checkIsAddingRoom(Scanner scanner) throws IllegalArgumentException {
        System.out.println("Would you like to add a another room? y/n");

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
}
