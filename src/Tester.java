import model.Customer;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@domain.com");

        System.out.println(customer);

        Customer invalidCustomer = new Customer("first", "second", "email");

        System.out.println(invalidCustomer);
    }
}
