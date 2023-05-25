package account;

import util.AccountNumberGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected int customerId;

    public Account(int customerId) {
        this.accountNumber = AccountNumberGenerator.generateAccountNumber();
        this.customerId = customerId;
        this.balance = 0.0;
    }
    public Account(ResultSet dataOut) throws SQLException {
        this.accountNumber = dataOut.getString("account_number");
        this.balance = dataOut.getDouble("balance");
        this.customerId = dataOut.getInt("customer_id");
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount);

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", customerId=" + customerId +
                '}';
    }
}
