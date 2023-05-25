package customer;

import java.util.Scanner;

public class CustomerMenu {
    private Scanner scanner;
    private CustomerService customerService;

    public CustomerMenu() {
        scanner = new Scanner(System.in);
        customerService = CustomerService.getInstance();
    }

    public void displayMenu() {
        boolean exit = false;
        int choice;

        while (!exit) {
            System.out.println("===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. Create Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. View Customer Details");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> customerService.createCustomer(scanner);
                case 2 -> customerService.updateCustomer(scanner);
                case 3 -> customerService.deleteCustomer(scanner);
                case 4 -> customerService.viewCustomerDetails(scanner);
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
