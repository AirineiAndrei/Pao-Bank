package transaction;

import java.util.Scanner;

public class TransactionMenu {
    private Scanner scanner;
    private TransactionService transactionService;

    public TransactionMenu() {
        scanner = new Scanner(System.in);
        transactionService = TransactionService.getInstance();
    }

    public void displayMenu() {
        boolean exit = false;
        int choice;

        while (!exit) {
            System.out.println("===== TRANSACTION MANAGEMENT =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> transactionService.deposit(scanner);
                case 2 -> transactionService.withdraw(scanner);
                case 3 -> transactionService.transfer(scanner);
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
