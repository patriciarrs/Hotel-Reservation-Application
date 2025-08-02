package utils;

import java.util.NoSuchElementException;
import java.util.Scanner;

public final class YesOrNoInput {
    private YesOrNoInput() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Get an Y (yes) or N (no) input from the user.
     *
     * @param message the message that asks for the user input.
     * @param scanner the text scanner input.
     * @return true if the user input Y.
     * @throws NoSuchElementException   if no line is found on the scanner.
     * @throws IllegalStateException    if the scanner is closed.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public static boolean getYesOrNo(String message, Scanner scanner)
            throws NoSuchElementException, IllegalStateException, IllegalArgumentException {
        System.out.println(message + " y/n");

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
