package account;

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
                case 1 -> accountService.login(scanner);
                case 2 -> accountService.logout();
                case 3 -> accountService.createAccount(scanner);
                case 4 -> accountService.deleteAccount(scanner);
                case 5 -> accountService.viewAccountDetails(scanner);
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createAccount() {
//        System.out.print("Enter customer ID: ");
//        int customerId = scanner.nextInt();
//        scanner.nextLine(); // Consume the newline character
//
//        System.out.print("Enter account type (CHECKING/SAVINGS): ");
//        AccountType accountType = AccountType.valueOf(scanner.nextLine());
//
//        Account account = accountService.createAccount(customerId, accountType);
//        if (account != null) {
//            System.out.println("account.Account created successfully.");
//            System.out.println("account.Account Number: " + account.getAccountNumber());
//        } else {
//            System.out.println("customer.Customer not found or invalid account type.");
//        }
    }

    private void updateAccount() {

    }

    private void deleteAccount() {
//        System.out.print("Enter account number: ");
//        String accountNumber = scanner.nextLine();
//
//        boolean result = accountService.deleteAccount(accountNumber);
//        if (result) {
//            System.out.println("account.Account deleted successfully.");
//        } else {
//            System.out.println("account.Account not found.");
//        }
    }

    private void viewAccountDetails() {
//        System.out.print("Enter account number: ");
//        String accountNumber = scanner.nextLine();
//
//        Account account = accountService.getAccountByNumber(accountNumber);
//        if (account != null) {
//            System.out.println("account.Account Details:");
//            System.out.println("account.Account Number: " + account.getAccountNumber());
//            System.out.println("customer.Customer ID: " + account.getCustomerId());
//            System.out.println("account.Account Type: " + account.getAccountType());
//        } else {
//            System.out.println("account.Account not found.");
//        }
    }
}
