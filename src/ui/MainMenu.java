package ui;

import java.util.Scanner;

/**
 * Main menu for the users who want to book a room.
 */
public class MainMenu {
    public static void main(String[] args) {
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
                    Please select a number for the menu option
                    """);

            String userInput = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
