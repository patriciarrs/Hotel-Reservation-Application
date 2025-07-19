package ui;

import java.util.Scanner;

/**
 * Admin menu for administrative functions.
 */
public class AdminMenu {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("""
                    Admin Menu
                    _______________________________________________
                    1. See all Customers
                    2. See all Rooms
                    3. See all Reservations
                    4. Add a Room
                    5. Back to Main Menu
                    _______________________________________________
                    Please select a number for the menu option
                    """);
            String userInput = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
