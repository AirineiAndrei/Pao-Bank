package menu;


import service.TransactionService;

import java.util.List;

public class TransactionMenu extends Menu {

    public TransactionMenu() {
        TransactionService transactionService = TransactionService.getInstance();
        menuOptions = List.of(
                new MenuOption("Deposit", transactionService::deposit),
                new MenuOption("Withdraw", transactionService::withdraw),
                new MenuOption("Transfer", transactionService::transfer),
                new MenuOption("Go Back", null)
        );
    }

    @Override
    protected String getMenuTitle() {
        return "TRANSACTION MANAGEMENT";
    }
}
