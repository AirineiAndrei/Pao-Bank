package util;

import java.util.Random;

public class AccountNumberGenerator {
    private static final int ACCOUNT_NUMBER_LENGTH = 15;
    private static final String ACCOUNT_NUMBER_CHARS = "0123456789";
    private static Random random = new Random(System.currentTimeMillis());

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int index = random.nextInt(ACCOUNT_NUMBER_CHARS.length());
            char randomChar = ACCOUNT_NUMBER_CHARS.charAt(index);
            accountNumber.append(randomChar);
        }
        return "ROAAC" + accountNumber;
    }
}

