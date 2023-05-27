package model.account;

import exception.InsufficientFundsException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(int customerId, double overdraftLimit) {
        super(customerId);
        this.overdraftLimit = overdraftLimit;
    }

    public CheckingAccount(ResultSet dataOut) throws SQLException {
        super(dataOut);
        this.overdraftLimit = dataOut.getDouble("overdraft_limit");
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        double availableBalance = getBalance() + overdraftLimit;
        if (availableBalance >= amount) {
            // Sufficient funds, perform withdrawal
            setBalance(getBalance() - amount);
            System.out.println("Withdrawal successful. Current balance: " + getBalance());
        } else {
            throw new InsufficientFundsException("Insufficient funds. Withdrawal rejected.");
        }
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "overdraftLimit=" + overdraftLimit +
                ", accountNumber='" + getAccountNumber() + '\'' +
                ", balance=" + getBalance() +
                ", customerId=" + getCustomerId() +
                '}';
    }
}
