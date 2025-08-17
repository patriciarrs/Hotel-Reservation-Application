import model.Customer;

import java.time.LocalDate;

public class Tester {
    public static void main(String[] args) {
        testIsBooked();
        testCustomer();
    }

    private static void testIsBooked() {
        futureIsNotBooked(); // expected result: false
        pastIsNotBooked(); // expected result: false
        futureIsBooked(); // expected result: true
        pastIsBooked(); // expected result: true
    }

    private static void testCustomer() {
        Customer customer = new Customer("first", "second", "j@domain.com");

        System.out.println(customer);

        Customer invalidCustomer = new Customer("first", "second", "email");

        System.out.println(invalidCustomer);
    }

    private static void futureIsNotBooked() {
        LocalDate existingCheckIn = LocalDate.of(2026, 1, 1);
        LocalDate existingCheckOut = LocalDate.of(2026, 1, 15);
        LocalDate desiredCheckIn = LocalDate.of(2026, 1, 15);
        LocalDate desiredCheckOut = LocalDate.of(2026, 1, 30);

        checkIsBooked(existingCheckIn, existingCheckOut, desiredCheckIn, desiredCheckOut);
    }

    private static void pastIsNotBooked() {
        LocalDate existingCheckIn = LocalDate.of(2026, 1, 15);
        LocalDate existingCheckOut = LocalDate.of(2026, 1, 30);
        LocalDate desiredCheckIn = LocalDate.of(2026, 1, 1);
        LocalDate desiredCheckOut = LocalDate.of(2026, 1, 15);

        checkIsBooked(existingCheckIn, existingCheckOut, desiredCheckIn, desiredCheckOut);
    }

    private static void futureIsBooked() {
        LocalDate existingCheckIn = LocalDate.of(2026, 1, 1);
        LocalDate existingCheckOut = LocalDate.of(2026, 1, 15);
        LocalDate desiredCheckIn = LocalDate.of(2026, 1, 14);
        LocalDate desiredCheckOut = LocalDate.of(2026, 1, 29);

        checkIsBooked(existingCheckIn, existingCheckOut, desiredCheckIn, desiredCheckOut);
    }

    private static void pastIsBooked() {
        LocalDate existingCheckIn = LocalDate.of(2026, 1, 15);
        LocalDate existingCheckOut = LocalDate.of(2026, 1, 30);
        LocalDate desiredCheckIn = LocalDate.of(2026, 1, 2);
        LocalDate desiredCheckOut = LocalDate.of(2026, 1, 16);

        checkIsBooked(existingCheckIn, existingCheckOut, desiredCheckIn, desiredCheckOut);
    }

    private static void checkIsBooked(LocalDate existingCheckIn, LocalDate existingCheckOut, LocalDate desiredCheckIn,
                                      LocalDate desiredCheckOut) {
        boolean isBooked = existingCheckIn.isBefore(desiredCheckOut) && desiredCheckIn.isBefore(existingCheckOut);

        System.out.println(isBooked);
    }
}
