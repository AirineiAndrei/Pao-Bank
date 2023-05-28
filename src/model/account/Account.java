package model.account;

import exception.InsufficientFundsException;
import exception.InvalidCurrencyException;
import model.currency.Currency;
import factory.CurrencyFactory;
import util.AccountNumberGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected int customerId;

    protected Currency currency;

    public Account(int customerId,String currencyCode) throws InvalidCurrencyException{
        this.accountNumber = AccountNumberGenerator.generateAccountNumber();
        this.customerId = customerId;
        this.balance = 0.0;
        this.currency = CurrencyFactory.getCurrencyByName(currencyCode);

        if(this.currency == null)
            throw new InvalidCurrencyException("This bank doesnt support this currency...");

    }

    public Account(ResultSet dataOut) throws SQLException {
        this.accountNumber = dataOut.getString("account_number");
        this.balance = dataOut.getDouble("balance");
        this.customerId = dataOut.getInt("customer_id");
        this.currency = CurrencyFactory.getCurrencyById(dataOut.getInt("currency_id"));
    }
    public boolean canTransfer(Account other)
    {
        return this.currency.equals(other.currency);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount) throws InsufficientFundsException;

    @Override
    public String toString() {
        return "Account:\n"
                + "Account Number: " + accountNumber + '\n'
                + "Balance: " + balance + " " + currency.getSymbol() + '\n'
                + "Customer ID: " + customerId + '\n';
    }


    public Currency getCurrency() {
        return currency;
    }
}
