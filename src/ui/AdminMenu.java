package ui;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Admin menu for administrative functions.
 */
final public class AdminMenu {
    AdminResource adminResource = AdminResource.getInstance();

    /**
     * Initialize the admin menu UI.
     */
    public void getMenu(Scanner scanner) {
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

        handleMenuOptionSelections(scanner);
    }

    /**
     * Handle the menu option selections.
     *
     * @param scanner the text scanner input.
     * @throws IllegalArgumentException if the selected option is not an integer, or does not exist.
     */
    private void handleMenuOptionSelections(Scanner scanner)
            throws IllegalArgumentException {
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
                    case 1 -> System.out.println("1");
                    case 2 -> System.out.println("2");
                    case 3 -> System.out.println("3");
                    case 4 -> addARoom(scanner);
                    case 5 -> scanner.close();
                }
            } catch (IllegalArgumentException e) {
                isInputValid = false;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                isInputValid = false;
            }
        } while (!isInputValid);
    }

    /**
     * Admin Menu Option 4: Add a new room.
     *
     * @param scanner the text scanner input.
     */
    private void addARoom(Scanner scanner) {
        boolean isAddingRoom;
        
        do {
            String number = getNumber(scanner);
            double price = getPrice(scanner);
            RoomType type = getType(scanner);

            Room room = new Room(number, price, type);
            adminResource.addRoom(room);

            isAddingRoom = checkIsAddingRoom(scanner);
        } while (isAddingRoom);
    }

    /**
     * Get the room number.
     *
     * @param scanner the text scanner input.
     * @return the room number.
     */
    private String getNumber(Scanner scanner) {
        do {
            try {
                System.out.println("Enter room number:");
                String input = scanner.nextLine();

                Collection<IRoom> rooms = adminResource.getAllRooms();

                if (rooms != null && rooms.stream().anyMatch(room -> input.equals(room.getNumber()))) {
                    throw new IllegalArgumentException("That room number is already in use.");
                }

                return input;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
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
                double doubleInput = Double.parseDouble(input);

                if (doubleInput < 0) {
                    throw new IllegalArgumentException("The price must be a positive number.");
                }

                return doubleInput;
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Enter a valid number (e.g., 199.99).");
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
    private RoomType getType(Scanner scanner) throws IllegalArgumentException {
        String message = "Enter room type (1 for single bed, 2 for double bed):";
        System.out.println(message);

        do {
            try {
                String input = scanner.nextLine();
                int intInput = parseInt(input);

                if (intInput != 1 && intInput != 2) {
                    throw new IllegalArgumentException("Only 1 or 2 are allowed.");
                }

                return intInput == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
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
