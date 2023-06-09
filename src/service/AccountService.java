package service;

import model.customer.Customer;
import database.DatabaseOperator;
import exception.FailedLoginException;
import exception.InsufficientFundsException;
import model.account.Account;
import model.account.AccountType;
import model.account.CheckingAccount;
import model.account.SavingsAccount;
import model.transaction.Transaction;

import java.sql.*;
import java.util.*;

public class AccountService {
    private static AccountService instance;
    private final Map<Integer, List<Account>> accountMap;
    private final CustomerService customerService;
    private Customer loggedCustomer;
    private final DatabaseOperator database = DatabaseOperator.getInstance();

    private AccountService() {
        accountMap = new HashMap<>();
        customerService = CustomerService.getInstance();
        loadState();
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void logout(Scanner in) {
        loggedCustomer = null;
    }

    public void login(Scanner in) throws FailedLoginException {
        loggedCustomer = customerService.login(in);
        if (loggedCustomer == null) {
            throw new FailedLoginException("Failed to authenticate. Please try again.");
        }
    }

    private void loadState() {
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts;");
            while (resultSet.next()) {
                String type = resultSet.getString("account_type").toUpperCase();
                AccountType accountType;
                Account account = null;
                try {
                    accountType = AccountType.valueOf(type);
                    switch (accountType) {
                        case CHECKING -> {
                            account = new CheckingAccount(resultSet);
                        }
                        case SAVINGS -> {
                            account = new SavingsAccount(resultSet);
                        }
                        default -> {
                            System.out.println("Unsupported account type. Exiting...");
                            return;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid account type. Exiting...");
                    // TO DO: throw custom exception
                }
                addAccount(account);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.print("Failed to load state of accounts database\n");
            System.out.println(e.toString());
        }
    }

    private void addAccount(Account account) {
        List<Account> accountList = accountMap.computeIfAbsent(account.getCustomerId(), k -> new ArrayList<>());
        accountList.add(account);
    }

    private void insertAccount(Account account) {
        String query = "INSERT INTO `pao`.`accounts`\n" +
                "(`customer_id`,\n" +
                "`account_number`,\n" +
                "`account_type`,\n" +
                "`balance`,\n" +
                "`overdraft_limit`,\n" +
                "`interest_rate`,\n" +
                "`currency_id`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?);\n";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, account.getCustomerId());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setDouble(4, account.getBalance());
            if (account instanceof CheckingAccount checkingAccount) {
                preparedStatement.setString(3, AccountType.CHECKING.name());
                preparedStatement.setDouble(5, checkingAccount.getOverdraftLimit());
                preparedStatement.setNull(6, Types.DOUBLE);
            }
            if (account instanceof SavingsAccount savingsAccount) {
                preparedStatement.setString(3, AccountType.SAVINGS.name());
                preparedStatement.setNull(5, Types.DOUBLE);
                preparedStatement.setDouble(6, savingsAccount.getInterestRate());
            }
            preparedStatement.setInt(7,account.getCurrency().getCurrencyId());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void createAccount(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to create accounts");
                login(in);
            }
            System.out.println("Enter account type (CHECKING or SAVINGS):");
            String input = in.nextLine().toUpperCase();

            AccountType accountType;
            try {
                accountType = AccountType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid account type. Exiting...");
                return;
            }

            System.out.println("Enter account currency");
            String currencyName = in.nextLine();

            Account account;
            switch (accountType) {
                case CHECKING -> {
                    System.out.println("Enter account overdraft limit");
                    double overdraftLimit = in.nextDouble();
                    account = new CheckingAccount(loggedCustomer.getId(),currencyName, overdraftLimit);
                }
                case SAVINGS -> {
                    System.out.println("Enter account interest rate");
                    double interestRate = in.nextDouble();
                    account = new SavingsAccount(loggedCustomer.getId(),currencyName, interestRate);
                }
                default -> {
                    System.out.println("Unsupported account type. Exiting...");
                    return;
                }
            }
            addAccount(account);
            insertAccount(account);
            System.out.println("Your new account number is " + account.getAccountNumber());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Account getAccountByNumber(String accountNumber) {
        if(accountMap.containsKey(loggedCustomer.getId()))
            for (Account account : accountMap.get(loggedCustomer.getId())) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
        return null;
    }

    public Account getAccountByNumberAcrossCustomers(String accountNumber) {
        for (Map.Entry<Integer, List<Account>> entry : accountMap.entrySet()) {
            List<Account> accounts = entry.getValue();
            for (Account account : accounts) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
        }
        return null;
    }


    public void viewAccountDetails(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to view account details");
                login(in);
            }
            System.out.print("Enter account number: ");
            String accountNumber = in.nextLine();
            Account account = getAccountByNumber(accountNumber);
            if (account != null) {
                System.out.println(account);
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to delete an account");
                login(in);
            }
            System.out.print("Enter account number: ");
            String accountNumber = in.nextLine();
            Account account = getAccountByNumber(accountNumber);
            if (account != null) {
                deleteAccount(account);
            } else {
                System.out.println("Account not found.");
            }
        } catch (FailedLoginException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(Account account) {
        try {
            accountMap.get(loggedCustomer.getId()).remove(account);
            String query = "DELETE FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void updateAccount(Account account) {
        try {
            String query = "UPDATE `pao`.`accounts`\n" +
                    "SET\n" +
                    "`customer_id` = ?,\n" +
                    "`account_number` = ?,\n" +
                    "`account_type` = ?,\n" +
                    "`balance` = ?,\n" +
                    "`overdraft_limit` = ?,\n" +
                    "`interest_rate` = ?\n" +
                    "WHERE `account_number` = ?;\n";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, account.getCustomerId());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setDouble(4, account.getBalance());
            if (account instanceof CheckingAccount checkingAccount) {
                preparedStatement.setString(3, AccountType.CHECKING.name());
                preparedStatement.setDouble(5, checkingAccount.getOverdraftLimit());
                preparedStatement.setNull(6, Types.DOUBLE);
            }
            if (account instanceof SavingsAccount savingsAccount) {
                preparedStatement.setString(3, AccountType.SAVINGS.name());
                preparedStatement.setNull(5, Types.DOUBLE);
                preparedStatement.setDouble(6, savingsAccount.getInterestRate());
            }
            preparedStatement.setString(7, account.getAccountNumber());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public Transaction deposit(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to make a deposit");
                login(in);
            }

            System.out.print("Enter account number: ");
            String accountNumber = in.nextLine();
            Account account = getAccountByNumber(accountNumber);

            if (account == null) {
                System.out.println("Account not found.");
                return null;
            }

            System.out.print("Enter the amount to deposit: ");
            double amount = in.nextDouble();
            account.deposit(amount);
            updateAccount(account);
            System.out.println("Deposit successful. New balance: " + account.getBalance());

            // Create and return the Transaction object
            String description = "Deposit";
            String transactionType = "Deposit";
            String sourceAccount = "";

            return new Transaction(accountNumber, description, amount, transactionType, sourceAccount, accountNumber);
        } catch (FailedLoginException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Transaction withdraw(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to make a withdrawal");
                login(in);
            }

            System.out.print("Enter account number: ");
            String accountNumber = in.nextLine();
            Account account = getAccountByNumber(accountNumber);

            if (account == null) {
                System.out.println("Account not found.");
                return null;
            }

            System.out.print("Enter the amount to withdraw: ");
            double amount = in.nextDouble();

            account.withdraw(amount);

            updateAccount(account);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());

            // Create and return the Transaction object
            String description = "Withdrawal";
            String transactionType = "Withdrawal";
            String destinationAccount = "";
            return new Transaction(accountNumber, description, amount, transactionType, accountNumber, destinationAccount);
        } catch (FailedLoginException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Transaction transfer(Scanner in) {
        try {
            if (loggedCustomer == null) {
                System.out.println("Please login to make a transfer");
                login(in);
            }

            System.out.print("Enter source account number: ");
            String sourceAccountNumber = in.nextLine();
            Account sourceAccount = getAccountByNumber(sourceAccountNumber);

            if (sourceAccount == null) {
                System.out.println("Source account not found.");
                return null;
            }

            System.out.print("Enter destination account number: ");
            String destinationAccountNumber = in.nextLine();
            Account destinationAccount = getAccountByNumberAcrossCustomers(destinationAccountNumber);

            if (destinationAccount == null) {
                System.out.println("Destination account not found.");
                return null;
            }

            if(!destinationAccount.canTransfer(sourceAccount)){
                System.out.println("These accounts have different currency, our bank doesnt support exchange yet...");
                System.out.println("We are sorry for the inconvenience");
                return null;
            }

            System.out.print("Enter the amount to transfer: ");
            double amount = in.nextDouble();

            sourceAccount.withdraw(amount);
            destinationAccount.deposit(amount);

            updateAccount(sourceAccount);
            updateAccount(destinationAccount);
            System.out.println("Transfer successful.");
            System.out.println("Source account balance: " + sourceAccount.getBalance());
            System.out.println("Destination account balance: " + destinationAccount.getBalance());

            // Create and return the Transaction object
            String description = "Transfer";
            String transactionType = "Transfer";
            return new Transaction(sourceAccountNumber, description, amount, transactionType, sourceAccountNumber, destinationAccountNumber);
        } catch (FailedLoginException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
