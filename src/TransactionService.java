import account.Account;
import account.AccountService;

public class TransactionService {
    private static TransactionService instance;
    private AccountService accountService;

    private TransactionService() {
        accountService = AccountService.getInstance();
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

//    public Transaction deposit(String accountNumber, double amount) {
//        Account account = accountService.getAccountByNumber(accountNumber);
//        if (account != null && amount > 0) {
//            Transaction transaction = new Transaction(accountNumber, amount);
//            return transaction;
//        }
//        return null;
//    }

//    public Transaction withdraw(String accountNumber, double amount) {
//        Account account = accountService.getAccountByNumber(accountNumber);
//        if (account != null && amount > 0) {
//            double balance = getAccountBalance(accountNumber);
//            if (balance >= amount) {
//                Transaction transaction = new Transaction(accountNumber, -amount);
//                return transaction;
//            }
//        }
//        return null;
//    }

//    public Transaction transfer(String sourceAccountNumber, String destinationAccountNumber, double amount) {
//        Account sourceAccount = accountService.getAccountByNumber(sourceAccountNumber);
//        Account destinationAccount = accountService.getAccountByNumber(destinationAccountNumber);
//        if (sourceAccount != null && destinationAccount != null && amount > 0) {
//            double sourceAccountBalance = getAccountBalance(sourceAccountNumber);
//            if (sourceAccountBalance >= amount) {
//                Transaction transaction = new Transaction(sourceAccountNumber, destinationAccountNumber, amount);
//                return transaction;
//            }
//        }
//        return null;
//    }

    private double getAccountBalance(String accountNumber) {
        // Retrieve account balance from the database
        return 0.0; // Placeholder value
    }
}
