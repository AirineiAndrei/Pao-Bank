package customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passwordHash;

    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public Customer(Scanner in)
    {
        read(in);
    }
    public Customer(ResultSet dataOut) throws SQLException {
        this.id = dataOut.getInt("id");
        this.firstName = dataOut.getString("first_name");
        this.lastName = dataOut.getString("last_name");
        this.email = dataOut.getString("email");
        this.passwordHash = dataOut.getString("password_hash");
        this.phoneNumber = dataOut.getString("phone_number");
    }
    void read(Scanner in)
    {
        System.out.print("Enter customer First Name: ");
        this.firstName = in.nextLine();
        System.out.print("Enter customer Last Name: ");
        this.lastName = in.nextLine();
        System.out.print("Enter customer email: ");
        this.email = in.nextLine();
        System.out.print("Enter customer password: ");
        this.passwordHash = hashPassword(in.nextLine());
        System.out.print("Enter customer phone number: ");
        this.phoneNumber = in.nextLine();
    }

    @Override
    public String toString() {
        return "customer.Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                "}\n";
    }
    public boolean authenticate(String password) {
        String hashedPassword = hashPassword(password);
        return passwordHash.equals(hashedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
