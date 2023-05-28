package menu;

import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu {

    private CustomerMenu customerMenu;
    private AccountMenu accountMenu;
    private TransactionMenu transactionMenu;

    public MainMenu() {
        super();
        customerMenu = new CustomerMenu();
        accountMenu = new AccountMenu();
        transactionMenu = new TransactionMenu();
        menuOptions = List.of(
                new MenuOption("Customer Management", customerMenu::displayMenu),
                new MenuOption("Account Management", accountMenu::displayMenu),
                new MenuOption("Transaction Management", transactionMenu::displayMenu),
                new MenuOption("Exit", null)
        );
    }

    @Override
    protected String getMenuTitle() {
        return "BANK MANAGEMENT SYSTEM";
    }
}
