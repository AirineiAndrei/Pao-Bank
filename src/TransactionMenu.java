

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
                case 1 -> deposit();
                case 2 -> withdraw();
                case 3 -> transfer();
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

//        Transaction transaction = transactionService.deposit(accountNumber, amount);
//        if (transaction != null) {
//            System.out.println("Deposit successful.");
//            System.out.println("Transaction ID: " + transaction.getTransactionId());
//            System.out.println("account.Account Number: " + transaction.getAccountNumber());
//            System.out.println("Amount: " + transaction.getAmount());
//        } else {
//            System.out.println("account.Account not found or invalid amount.");
//        }
    }

    private void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

//        Transaction transaction = transactionService.withdraw(accountNumber, amount);
//        if (transaction != null) {
//            System.out.println("Withdrawal successful.");
//            System.out.println("Transaction ID: " + transaction.getTransactionId());
//            System.out.println("account.Account Number: " + transaction.getAccountNumber());
//            System.out.println("Amount: " + transaction.getAmount());
//        } else {
//            System.out.println("account.Account not found or insufficient balance.");
//        }
    }

    private void transfer() {
        System.out.print("Enter source account number: ");
        String sourceAccountNumber = scanner.nextLine();

        System.out.print("Enter destination account number: ");
        String destinationAccountNumber = scanner.nextLine();

        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

//        Transaction transaction = transactionService.transfer(sourceAccountNumber, destinationAccountNumber, amount);
//        if (transaction != null) {
//            System.out.println("Transfer successful.");
//            System.out.println("Transaction ID: " + transaction.getTransactionId());
//            System.out.println("Source account.Account Number: " + transaction.getSourceAccountNumber());
//            System.out.println("Destination account.Account Number: " + transaction.getDestinationAccountNumber());
//            System.out.println("Amount: " + transaction.getAmount());
//        } else {
//            System.out.println("Accounts not found or insufficient balance.");
//        }
    }
}
