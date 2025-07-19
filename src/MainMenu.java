import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("""
                    1. Find and reserve a room
                    2. See my reservations
                    3. Create an account
                    4. Admin
                    5. Exit
                    """);

            String userInput = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
