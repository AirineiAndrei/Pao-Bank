package service;

import database.DatabaseOperator;
import model.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionService {
    private static TransactionService instance;
    private List<Transaction> transactions;
    private AccountService accountService;
    DatabaseOperator database = DatabaseOperator.getInstance();
    private void loadState() {
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions;");
            while (resultSet.next()) {
                Transaction transaction = new Transaction(resultSet);
                transactions.add(transaction);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.print("Failed to load state of customers database\n");
            System.out.println(e.toString());
        }
        System.out.println(transactions);
    }
    private TransactionService() {
        transactions = new ArrayList<>();
        accountService = AccountService.getInstance();
        loadState();
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }
    private void insertTransaction(Transaction transaction){
        transactions.add(transaction);
        String query = "INSERT INTO `pao`.`transactions`\n" +
                "(" +
                "`account_number`,\n" +
                "`timestamp`,\n" +
                "`description`,\n" +
                "`amount`,\n" +
                "`transaction_type`,\n" +
                "`source_account`,\n" +
                "`destination_account`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?);\n";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1,transaction.getAccountNumber());
            preparedStatement.setDate(2, (new java.sql.Date(transaction.getTimestamp().getTime())));
            preparedStatement.setString(3,transaction.getDescription());
            preparedStatement.setDouble(4,transaction.getAmount());
            preparedStatement.setString(5,transaction.getTransactionType());
            preparedStatement.setString(6,transaction.getSourceAccount());
            preparedStatement.setString(7,transaction.getDestinationAccount());
            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void updateTransactionInDatabase(Transaction transaction) {
        String query = "UPDATE transactions SET description = ? WHERE transaction_id = ?";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, transaction.getDescription());
            preparedStatement.setInt(2, transaction.getTransactionId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void deleteTransactionFromDatabase(int transactionId) {
        String query = "DELETE FROM transactions WHERE transaction_id = ?";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void deposit(Scanner in)
    {
        Transaction transaction = accountService.deposit(in);
        if(transaction != null)
            insertTransaction(transaction);
    }
    public void withdraw(Scanner in)
    {
        Transaction transaction = accountService.withdraw(in);
        if(transaction != null)
            insertTransaction(transaction);
    }
    public void transfer(Scanner in) {
        Transaction transaction = accountService.transfer(in);
        if(transaction != null)
            insertTransaction(transaction);
    }
}
