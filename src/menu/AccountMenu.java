package menu;

import exception.FailedLoginException;
import service.AccountService;

import java.util.List;
import java.util.Scanner;

public class AccountMenu extends Menu {
    private final AccountService accountService;

    public AccountMenu() {
        accountService = AccountService.getInstance();
        menuOptions = List.of(
                new MenuOption("Login", this::login),
                new MenuOption("Logout", accountService::logout),
                new MenuOption("Create Account", accountService::createAccount),
                new MenuOption("Delete Account", accountService::deleteAccount),
                new MenuOption("View Account Details", accountService::viewAccountDetails),
                new MenuOption("Go Back", null)
        );
    }

    @Override
    protected String getMenuTitle() {
        return "ACCOUNT MANAGEMENT";
    }

    private void login(Scanner in) {
        try {
            accountService.login(in);
        } catch (FailedLoginException e) {
            System.out.println(e.toString());
        }
    }
}
