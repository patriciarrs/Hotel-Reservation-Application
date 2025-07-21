package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

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

        scanner.close();
    }

    /**
     * Handle the menu option selections.
     *
     * @param scanner             the text scanner input.
     * @param selectOptionMessage the message for selecting an option.
     * @throws InputMismatchException   if the selected option is not a number.
     * @throws IllegalArgumentException if the selected option does not exist.
     */
    private void handleMenuOptionSelections(Scanner scanner, String selectOptionMessage)
            throws InputMismatchException, IllegalArgumentException {
        boolean isInputValid = false;

        while (!isInputValid) {
            try {
                int option = scanner.nextInt();

                if (option < 1 || option > 5) {
                    throw new IllegalArgumentException(selectOptionMessage);
                }

                isInputValid = true;
                scanner.nextLine(); // throw away the \n not consumed by nextInt()

                switch (option) {
                    case 1:
                        findAndReserveARoom(scanner);
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                    case 4:
                        System.out.println("4");
                        break;
                    case 5:
                        scanner.close();
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                scanner.nextLine(); // throw away the \n not consumed by nextInt()
                System.out.println(selectOptionMessage);
                isInputValid = false;
            } catch (Exception e) {
                scanner.nextLine(); // throw away the \n not consumed by nextInt()
                System.out.println(e.getMessage());
                isInputValid = false;
            }
        }
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
     * @throws IllegalArgumentException if the check-in date is before today.
     */
    private LocalDate getCheckInDate(Scanner scanner, DateTimeFormatter formatter) throws IllegalArgumentException {
        while (true) {
            try {
                System.out.println("Enter check-in date as yyyy/MM/dd (e.g., 2026/01/01)");
                String checkInInput = scanner.nextLine();

                LocalDate checkIn = LocalDate.parse(checkInInput, formatter);

                if (checkIn.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Enter a check-in date in the future.");
                }

                return checkIn;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Get the check-out date.
     *
     * @param scanner     the text scanner input.
     * @param formatter   the date formatter.
     * @param checkInDate the check-in date.
     * @return the check-out date.
     * @throws IllegalArgumentException if the check-out date is before the check-in date.
     */
    private LocalDate getCheckOutDate(Scanner scanner, DateTimeFormatter formatter, LocalDate checkInDate)
            throws IllegalArgumentException {
        while (true) {
            try {
                System.out.println("Enter check-out date as yyyy/MM/dd (e.g., 2026/01/15)");
                String checkOutInput = scanner.nextLine();

                LocalDate checkOut = LocalDate.parse(checkOutInput, formatter);

                if (checkOut.isBefore(checkInDate)) {
                    throw new IllegalArgumentException("Enter a check-out date that is after the check-in.");
                }

                return checkOut;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * TODO
     */
    private void reserveARoom() {
        System.out.println("Do you have and account with us? y/n");
    }
}
