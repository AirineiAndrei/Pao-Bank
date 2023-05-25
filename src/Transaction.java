
public class Transaction {
    private static int nextTransactionId = 1;

    private int transactionId;
    private String accountNumber;
    private double amount;
    private String sourceAccountNumber;
    private String destinationAccountNumber;

    public Transaction(String accountNumber, double amount) {
        this.transactionId = nextTransactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        nextTransactionId++;
    }

    public Transaction(String sourceAccountNumber, String destinationAccountNumber, double amount) {
        this.transactionId = nextTransactionId;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        nextTransactionId++;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }
}
