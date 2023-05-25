// com.bank.menu.Menu.java


import account.AccountMenu;
import customer.CustomerMenu;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private CustomerMenu customerMenu;
    private AccountMenu accountMenu;
    private TransactionMenu transactionMenu;

    public Menu() {
        scanner = new Scanner(System.in);
        customerMenu = new CustomerMenu();
        accountMenu = new AccountMenu();
        transactionMenu = new TransactionMenu();
    }

    public void displayMenu() {
        boolean exit = false;
        int choice;

        while (!exit) {
            System.out.println("===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. customer.Customer Management");
            System.out.println("2. account.Account Management");
            System.out.println("3. Transaction Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    customerMenu.displayMenu();
                    break;
                case 2:
                    accountMenu.displayMenu();
                    break;
                case 3:
                    transactionMenu.displayMenu();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
