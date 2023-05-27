package account;

import customer.Customer;
import customer.CustomerService;
import database.DatabaseOperator;
import transaction.Transaction;

import java.sql.*;
import java.util.*;

public class AccountService {
    private static AccountService instance;
    private Map<Integer, List<Account>> accountMap;
    private CustomerService customerService;
    private Customer loggedCustomer;
    DatabaseOperator database = DatabaseOperator.getInstance();

    private void loadState()
    {
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts;");
            while(resultSet.next())
            {
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
        System.out.println(accountMap.toString());
    }
    private void addAccount(Account account)
    {
        List<Account> accountList = accountMap.computeIfAbsent(account.getCustomerId(), k -> new ArrayList<>());
        accountList.add(account);
    }

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

    public void logout()
    {
        loggedCustomer = null;
    }
    public void login(Scanner in)
    {
        loggedCustomer = customerService.login(in);
    }
    private void insertAccount(Account account)
    {
        String query = "INSERT INTO `pao`.`accounts`\n" +
                "(`customer_id`,\n" +
                "`account_number`,\n" +
                "`account_type`,\n" +
                "`balance`,\n" +
                "`overdraft_limit`,\n" +
                "`interest_rate`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?);\n";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,account.getCustomerId());
            preparedStatement.setString(2,account.getAccountNumber());
            preparedStatement.setDouble(4,account.getBalance());
            if(account instanceof CheckingAccount checkingAccount)
            {
                preparedStatement.setString(3,AccountType.CHECKING.name());
                preparedStatement.setDouble(5,checkingAccount.getOverdraftLimit());
                preparedStatement.setNull(6, Types.DOUBLE);
            }
            if(account instanceof SavingsAccount savingsAccount)
            {
                preparedStatement.setString(3,AccountType.SAVINGS.name());
                preparedStatement.setNull(5, Types.DOUBLE);
                preparedStatement.setDouble(6,savingsAccount.getInterestRate());
            }
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public void createAccount(Scanner in)
    {
        if(loggedCustomer == null)
        {
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

        Account account;
        switch (accountType) {
            case CHECKING -> {
                System.out.println("Enter account over draft limit");
                double overDraftLimit = in.nextDouble();
                account = new CheckingAccount(loggedCustomer.getId(), overDraftLimit);
            }
            case SAVINGS -> {
                System.out.println("Enter account interest rate");
                double interestRate = in.nextDouble();
                account = new SavingsAccount(loggedCustomer.getId(), interestRate);
            }
            default -> {
                System.out.println("Unsupported account type. Exiting...");
                return;
            }
        }
        addAccount(account);
        insertAccount(account);
        System.out.println("Your new account number is " + account.getAccountNumber());
    }


    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accountMap.get(loggedCustomer.getId())) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    public void viewAccountDetails(Scanner in)
    {
        if(loggedCustomer == null)
        {
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
    }
    public void deleteAccount(Scanner in)
    {
        if(loggedCustomer == null)
        {
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
    }
    public void deleteAccount(Account account) {
        try{
            accountMap.get(loggedCustomer.getId()).remove(account);
            String query = "DELETE FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1,account.getAccountNumber());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void updateAccount(Account account)
    {
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
            preparedStatement.setInt(1,account.getCustomerId());
            preparedStatement.setString(2,account.getAccountNumber());
            preparedStatement.setDouble(4,account.getBalance());
            if(account instanceof CheckingAccount checkingAccount)
            {
                preparedStatement.setString(3,AccountType.CHECKING.name());
                preparedStatement.setDouble(5,checkingAccount.getOverdraftLimit());
                preparedStatement.setNull(6, Types.DOUBLE);
            }
            if(account instanceof SavingsAccount savingsAccount)
            {
                preparedStatement.setString(3,AccountType.SAVINGS.name());
                preparedStatement.setNull(5, Types.DOUBLE);
                preparedStatement.setDouble(6,savingsAccount.getInterestRate());
            }
            preparedStatement.setString(7, account.getAccountNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public Transaction deposit(Scanner in)
    {
        if(loggedCustomer == null)
        {
            System.out.println("Please login to deposit");
            login(in);
        }
        System.out.print("Enter account number: ");
        String accountNumber = in.nextLine();
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("Enter amount to deposit:");
            double amount = in.nextInt();
            account.deposit(amount);
            updateAccount(account);
            return new Transaction(account.getAccountNumber(),
                                    "Deposit manual",
                                    amount,
                                    "Deposit",
                                    null,
                                    null);
        } else {
            System.out.println("Account not found.");
            return null;
        }
    }

    public Transaction withdraw(Scanner in) {
        if(loggedCustomer == null)
        {
            System.out.println("Please login to deposit");
            login(in);
        }
        System.out.print("Enter account number: ");
        String accountNumber = in.nextLine();
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("Enter amount to withdraw:");
            double amount = in.nextInt();
            account.withdraw(amount);
            updateAccount(account);
            return new Transaction(account.getAccountNumber(),
                    "Withdraw manual",
                    amount,
                    "Withdraw",
                    null,
                    null);
        } else {
            System.out.println("Account not found.");
            return null;
        }
    }

    public Transaction transfer(Scanner in) {
        if (loggedCustomer == null) {
            System.out.println("Please login to deposit");
            login(in);
        }
        //TODO
        return null;
    }
}
