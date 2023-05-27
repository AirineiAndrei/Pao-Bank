package customer;

import database.DatabaseOperator;
import exception.FailedLoginException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CustomerService {
    private static CustomerService instance;
    private List<Customer> customers;
    private DatabaseOperator database;

    private CustomerService() {
        customers = new ArrayList<>();
        database = DatabaseOperator.getInstance();
        loadState();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    private void loadState() {
        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers;");
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet);
                customers.add(customer);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to load state of customers database");
            System.out.println(e.toString());
        }
    }

    public void createCustomer(Scanner in) {
        Customer customer = new Customer(in);
        customers.add(customer);
        insertCustomer(customer);
    }

    private void insertCustomer(Customer customer) {
        String query = "INSERT INTO `pao`.`customers` (`first_name`, `last_name`, `email`, `password_hash`, `phone_number`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPasswordHash());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.execute();
            preparedStatement.close();

            String queryId = "SELECT id FROM customers WHERE email = ?";
            PreparedStatement getIdQuery = database.getConnection().prepareStatement(queryId);
            getIdQuery.setString(1, customer.getEmail());
            ResultSet resultSet = getIdQuery.executeQuery();
            resultSet.next();
            customer.setId(Integer.parseInt(resultSet.getString("id")));
            getIdQuery.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void updateCustomer(Customer customer) {
        try {
            String query = "UPDATE `pao`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password_hash` = ?, `phone_number` = ? WHERE `id` = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPasswordHash());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void updateCustomer(Scanner in) {
        System.out.print("Enter customer email: ");
        String email = in.nextLine();
        Customer customer = getCustomerByEmail(email);
        if (customer != null) {
            customer.read(in);
            updateCustomer(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private Customer getCustomerByEmail(String email) {
        for (Customer customer : customers) {
            if (Objects.equals(customer.getEmail(), email)) {
                return customer;
            }
        }
        return null;
    }

    public void viewCustomerDetails(Scanner in) {
        System.out.print("Enter customer email: ");
        String email = in.nextLine();
        Customer customer = getCustomerByEmail(email);
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void deleteCustomer(Customer customer) {
        try {
            customers.remove(customer);
            String query = "DELETE FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean deleteCustomer(Scanner in) {
        System.out.print("Enter customer email: ");
        String email = in.nextLine();
        Customer customer = getCustomerByEmail(email);
        if (customer != null) {
            deleteCustomer(customer);
            return true;
        } else {
            System.out.println("Customer not found.");
            return false;
        }
    }

    public Customer login(Scanner in) throws FailedLoginException {
        System.out.print("Enter customer email: ");
        String email = in.nextLine();
        Customer customer = getCustomerByEmail(email);
        if (customer != null) {
            System.out.print("Enter customer password: ");
            String password = in.nextLine();
            if (customer.authenticate(password)) {
                return customer;
            } else {
                throw new FailedLoginException("Incorrect password");
            }
        } else {
            throw new FailedLoginException("Customer not found");
        }
    }
}
