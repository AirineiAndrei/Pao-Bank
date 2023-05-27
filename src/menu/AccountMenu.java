package menu;

import exception.FailedLoginException;
import service.AccountService;

import java.util.Scanner;

public class AccountMenu {
    private Scanner scanner;
    private AccountService accountService;

    public AccountMenu() {
        scanner = new Scanner(System.in);
        accountService = AccountService.getInstance();
    }

    public void displayMenu() {
        boolean exit = false;
        int choice;

        while (!exit) {
            System.out.println("===== ACCOUNT MANAGEMENT =====");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Create Account");
            System.out.println("4. Delete Account");
            System.out.println("5. View Account Details");
            System.out.println("6. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    try {
                        accountService.login(scanner);
                    } catch (FailedLoginException e) {
                        System.out.println(e.toString());
                    }
                }
                case 2 -> accountService.logout();
                case 3 -> accountService.createAccount(scanner);
                case 4 -> accountService.deleteAccount(scanner);
                case 5 -> accountService.viewAccountDetails(scanner);
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
