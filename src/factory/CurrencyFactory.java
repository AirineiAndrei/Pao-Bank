package factory;

import database.DatabaseOperator;
import model.currency.Currency;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CurrencyFactory {
    private static final CurrencyFactory instance = new CurrencyFactory();
    static private Set<Currency> currencies;
    private final DatabaseOperator database;


    private CurrencyFactory() {
        currencies = new HashSet<>();
        database = DatabaseOperator.getInstance();
        loadCurrencies();
    }

    public static CurrencyFactory getInstance() {
        return instance;
    }

    private void loadCurrencies() {
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM currencies;");
            while (resultSet.next()) {
                Currency currency = new Currency(resultSet);
                currencies.add(currency);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to load currencies from the database");
            System.out.println(e);
        }
    }


    static public Currency getCurrencyByName(String currencyName) {
        for (Currency currency : currencies) {
            if (currency.getCurrencyCode().equalsIgnoreCase(currencyName)) {
                return new Currency(currency);
            }
        }
        return null;
    }
    static public Currency getCurrencyById(int currencyId) {
        for (Currency currency : currencies) {
            if (currency.getCurrencyId() == currencyId) {
                return currency;
            }
        }
        return null;
    }

}

