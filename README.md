# Pao-Bank

**Bank Management System**

The Bank Management System is a software application designed to facilitate the management of customer accounts and transactions in a bank. It provides various functionalities to bank administrators and employees for efficient handling of customer data and financial operations.

**Features:**

1. Customer Management: The application allows administrators to create, update, and delete customer records. It captures essential customer information such as name, email, phone number, and account details.

2. Account Management: Bank employees can manage customer accounts through the application. They can create new accounts, view account details, and perform operations like deposit, withdrawal, and fund transfers.

3. Transaction Management: The system enables the recording and tracking of all transactions within the bank. It maintains a comprehensive log of transaction details, including timestamps, transaction types, amounts, and associated accounts.

4. User Authentication: The application incorporates secure user authentication mechanisms to ensure authorized access. It validates user credentials and enforces proper authentication protocols for secure usage.

5. Audit Logging: An audit service captures and logs all interactions and activities performed within the system. It maintains a record of actions, timestamps, and relevant details for security and auditing purposes.

6. Currency Management: The system supports multiple currencies and allows administrators to manage currency information such as currency codes, names, and symbols.

The Bank Management System provides a robust and user-friendly interface for bank administrators and employees to efficiently manage customer accounts, perform financial transactions, and ensure the security and integrity of the banking operations.

# Interogari

- Create Customer
- Update Customer
- Delete Customer
- View Customer Details
- Login
- Logout
- Create Account
- Delete Account
- View Account Details
- Deposit
- Withdraw
- Transfer (between account of the same currency)

# Database schema

**customers**
| Column Name   | Data Type     | Description                       |
|---------------|---------------|-----------------------------------|
| id            | int           | Unique identifier for the customer |
| first_name    | varchar(50)   | First name of the customer        |
| last_name     | varchar(50)   | Last name of the customer         |
| email         | varchar(100)  | Email of the customer             |
| password_hash | varchar(64)   | Password hash of the customer     |
| phone_number  | varchar(20)   | Phone number of the customer      |

**accounts**
| Column Name       | Data Type     | Description                                     |
|-------------------|---------------|-------------------------------------------------|
| customer_id       | int           | ID of the associated customer                    |
| account_number    | char(20)      | Account number                                  |
| account_type      | enum          | Type of the account (Checking or Savings)        |
| balance           | decimal(10,2) | Account balance                                 |
| overdraft_limit   | decimal(10,2) | Overdraft limit (optional)                       |
| interest_rate     | decimal(4,2)  | Interest rate (optional)                         |
| currency_id       | int           | ID of the associated currency                    |

**transactions**
| Column Name           | Data Type     | Description                                     |
|-----------------------|---------------|-------------------------------------------------|
| transaction_id        | int           | Unique identifier for the transaction            |
| account_number        | char(20)      | Account number associated with the transaction   |
| timestamp             | datetime      | Timestamp of the transaction                     |
| description           | varchar(255)  | Description of the transaction                   |
| amount                | decimal(10,2) | Amount of the transaction                        |
| transaction_type      | varchar(20)   | Type of the transaction                          |
| source_account        | char(20)      | Source account for transfer transactions         |
| destination_account   | char(20)      | Destination account for transfer transactions    |

**currencies**
| Column Name     | Data Type     | Description                       |
|-----------------|---------------|-----------------------------------|
| currency_id     | int           | Unique identifier for the currency |
| currency_code   | varchar(3)    | Currency code                     |
| currency_name   | varchar(50)   | Currency name                     |
| symbol          | varchar(10)   | Currency symbol (optional)        |
