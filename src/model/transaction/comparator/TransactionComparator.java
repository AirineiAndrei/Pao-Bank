package model.transaction.comparator;

import model.transaction.Transaction;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction t1, Transaction t2) {
        return t1.getTimestamp().compareTo(t2.getTimestamp());
    }
}