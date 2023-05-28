package model.currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Currency {
    private int currencyId;
    private String currencyCode;
    private String currencyName;
    private String symbol;

    public Currency(int currencyId, String currencyCode, String currencyName, String symbol) {
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.symbol = symbol;
    }
    public Currency(ResultSet dataOut) throws SQLException {
        this.currencyId = dataOut.getInt("currency_id");
        this.currencyCode = dataOut.getString("currency_code");
        this.currencyName = dataOut.getString("currency_name");
        this.symbol = dataOut.getString("symbol");
    }
    public Currency(Currency other) {
        this.currencyId = other.currencyId;
        this.currencyCode = other.currencyCode;
        this.currencyName = other.currencyName;
        this.symbol = other.symbol;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyId=" + currencyId +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        return currencyId == currency.currencyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyId);
    }
}
