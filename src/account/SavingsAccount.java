package account;

import exception.InsufficientFundsException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(int customerId, double interestRate) {
        super(customerId);
        this.interestRate = interestRate;
    }

    public SavingsAccount(ResultSet dataOut) throws SQLException {
        super(dataOut);
        this.interestRate = dataOut.getDouble("interest_rate");
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (getBalance() >= amount) {
            // Sufficient funds, perform withdrawal
            setBalance(getBalance() - amount);
            System.out.println("Withdrawal successful. Current balance: " + getBalance());
        } else {
            throw new InsufficientFundsException("Insufficient funds. Withdrawal rejected.");
        }
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "interestRate=" + interestRate +
                ", accountNumber='" + getAccountNumber() + '\'' +
                ", balance=" + getBalance() +
                ", customerId=" + getCustomerId() +
                '}';
    }

}

