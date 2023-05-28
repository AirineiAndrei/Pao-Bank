package menu;

import service.CustomerService;

import java.util.List;

public class CustomerMenu extends Menu {
    private final CustomerService customerService;
    public CustomerMenu() {
        customerService = CustomerService.getInstance();
        menuOptions = List.of(
                new MenuOption("Create Customer", customerService::createCustomer),
                new MenuOption("Update Customer", customerService::updateCustomer),
                new MenuOption("Delete Customer", customerService::deleteCustomer),
                new MenuOption("View Customer Details", customerService::viewCustomerDetails),
                new MenuOption("Go Back", null)
        );
    }

    @Override
    protected String getMenuTitle() {
        return "CUSTOMER MANAGEMENT";
    }
}