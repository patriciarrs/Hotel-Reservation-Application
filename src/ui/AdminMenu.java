package ui;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import utils.YesOrNoInput;

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
        System.out.println("""
                
                Admin Menu
                _______________________________________________
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                _______________________________________________
                """);

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
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                    case 4:
                        addARoom(scanner);
                        break;
                    case 5:
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.getMenu();
                        break;
                }

                getMenu(scanner);
            } catch (IllegalArgumentException e) {
                isInputValid = false;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                isInputValid = false;
            }
        } while (!isInputValid);
    }

    /**
     * Admin Menu Option 1: See all customers.
     */
    private void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    /**
     * Admin Menu Option 2: See all rooms.
     */
    private void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        for (IRoom room : rooms) {
            System.out.println(room);
        }
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

            isAddingRoom = YesOrNoInput.getYesOrNo("Would you like to add a another room?", scanner);
        } while (isAddingRoom);
    }

    /**
     * Get the room number.
     *
     * @param scanner the text scanner input.
     * @return the room number.
     * @throws IllegalArgumentException if the room number already exists.
     */
    private String getNumber(Scanner scanner) throws IllegalArgumentException {
        do {
            try {
                System.out.println("Enter room number:");
                String input = scanner.nextLine();

                Collection<IRoom> rooms = adminResource.getAllRooms();

                if (rooms.stream().anyMatch(room -> input.equals(room.getNumber()))) {
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
     * @throws NullPointerException     if the price input is null.
     * @throws NumberFormatException    if the price input is not a parsable double.
     * @throws IllegalArgumentException if the price input is not a positive double.
     */
    private double getPrice(Scanner scanner)
            throws NullPointerException, IllegalArgumentException {
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
     * @throws NumberFormatException    if the input is not a parsable integer.
     * @throws IllegalArgumentException if the selected option in not 1 or 2.
     */
    private RoomType getType(Scanner scanner) throws NumberFormatException, IllegalArgumentException {
        System.out.println("Enter room type (1 for single bed, 2 for double bed):");

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
}
