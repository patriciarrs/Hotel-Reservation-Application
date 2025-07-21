package ui;

import api.HotelResource;
import model.IRoom;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

/**
 * Main menu for the users who want to book a room.
 */
public class MainMenu {
    public void getMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("""
                    Welcome to the Hotel Reservation Application
                    
                    _______________________________________________
                    1. Find and reserve a room
                    2. See my reservations
                    3. Create an account
                    4. Admin
                    5. Exit
                    _______________________________________________
                    Please select a number for the menu option.
                    """);

            String option = scanner.nextLine();
            /*switch (option) {
                case "1":
                    findAndReserveARoom(scanner);
                    break;
                case "2":
                    System.out.println("2");
                    break;
                case "3":
                    System.out.println("3");
                    break;
                case "4":
                    System.out.println("4");
                    break;
                case "5":
                    scanner.close();
                default:
                    System.out.println("Please select a number from the menu option.");
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findAndReserveARoom(Scanner scanner) {
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
        String checkInInput = scanner.nextLine();
        LocalDate checkIn = LocalDate.parse(checkInInput);

        System.out.println("Enter CheckOut Date month/day/year example 2/21/2020");
        String checkOutInput = scanner.nextLine();
        LocalDate checkOut = LocalDate.parse(checkOutInput);

        HotelResource hotelResource = HotelResource.getInstance();
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

    }

    private void reserveARoom() {
        System.out.println("Do you have and account with us? y/n");
    }
}
