package transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Transaction {
    private String accountNumber;
    private Date timestamp;
    private String description;
    private double amount;
    private String transactionType;
    private String sourceAccount;
    private String destinationAccount;

    public Transaction(int transactionId, String accountNumber, Date timestamp, String description, double amount,
                       String transactionType, String sourceAccount, String destinationAccount) {
        this.accountNumber = accountNumber;
        this.timestamp = timestamp;
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    public Transaction(String accountNumber, String description, double amount,
                       String transactionType, String sourceAccount, String destinationAccount) {
        this.accountNumber = accountNumber;
        this.timestamp = new Date();
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }
    public Transaction(ResultSet dataOut) throws SQLException {
        this.accountNumber = dataOut.getString("account_number");
        this.timestamp = dataOut.getDate("timestamp");
        this.description = dataOut.getString("description");
        this.amount = dataOut.getDouble("amount");
        this.transactionType = dataOut.getString("transaction_type");
        this.sourceAccount = dataOut.getString("source_account");
        this.destinationAccount = dataOut.getString("destination_account");
    }
    // Getter and Setter methods


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                '}';
    }
}

