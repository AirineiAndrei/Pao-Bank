package menu;

import audit.AuditService;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Consumer;

public abstract class Menu {
    protected List<MenuOption> menuOptions;
    private final AuditService auditService;

    public Menu() {

        auditService = AuditService.getInstance();
    }

    public void displayMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("===== " + getMenuTitle() + " =====");

            // Display the menu options
            for (int i = 0; i < menuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + menuOptions.get(i).optionName());
            }

            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= menuOptions.size()) {
                    MenuOption selectedOption = menuOptions.get(choice - 1);

                    if (selectedOption.action() == null) {
                        exit = true;
                    } else {
                        performAction(selectedOption.optionName(), selectedOption.action(),scanner);
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    protected abstract String getMenuTitle();

    private void performAction(String actionName, Consumer<Scanner> action, Scanner scanner) {
        auditService.writeAuditEntry(actionName);
        action.accept(scanner);
    }
}
