package menu;

import java.util.List;

public class MainMenu extends Menu {

    public MainMenu() {
        super();
        CustomerMenu customerMenu = new CustomerMenu();
        AccountMenu accountMenu = new AccountMenu();
        TransactionMenu transactionMenu = new TransactionMenu();
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
