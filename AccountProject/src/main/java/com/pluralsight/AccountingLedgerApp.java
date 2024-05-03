package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class AccountingLedgerApp
{
    private Scanner userInput = new Scanner(System.in);

    public void run()
    {
        while (true) {
            String choice = displayHomeChoices();


            switch (choice.toLowerCase()) {
                case "d":
                    promptDeposit();
                    break;
                case "p":
                    makeAPayment();
                    break;
                case "l":
                    promptLedger();
                    break;
                case "x":
                    System.out.println("GoodBye, See you soon! ");
                    return;


            }
        }
    }

    public String displayHomeChoices()
    {
        while (true) {
            try {
                System.out.println();
                System.out.println("Welcome to you're Handy Dandy Account Ledger");
                System.out.println("*".repeat(50));
                System.out.println("D) Add a Deposit");
                System.out.println("P) Log in a payment with Debit/Credit Card");
                System.out.println("L) Display Ledgers ");
                System.out.println("X) Exit");
                System.out.println("*".repeat(50));
                System.out.println();
                System.out.println("Please select an option: ");
                return userInput.nextLine().strip().toLowerCase();
            } catch (Exception ex) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    private void promptDeposit()
    {
        System.out.println();
        System.out.println("Hello, let's log in a deposit! ");
        System.out.println("*".repeat(50));
        System.out.println("A) Add deposit ");
        System.out.println("X) Go back to Home Screen ");
        System.out.println("*".repeat(50));
        System.out.println("Please select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch (choice) {
            case "a":
                addDeposits();
                break;
            case "x":
                System.out.println("See you soon, Going back to Home Screen ");
                break;
            default:
                System.out.println("Invalid option, please select another one ");
        }
    }

    private void addDeposits()
    {
        System.out.println();
        System.out.println("Can You please provide the name of the vendor/client (ex. Amazon, UPS, Netflix,Client A): ");
        String vendor = userInput.nextLine();
        System.out.println("What is the amount of the deposit? ");
        double amount = Double.parseDouble(userInput.nextLine().strip());
        System.out.println("Can you please specify the purpose of this deposit? For example, is it a pre/payment for goods/services, a security deposit, or another type of deposit? : ");
        String description = userInput.nextLine();
        System.out.println("You're Deposit has been Added successfully! ");
        System.out.println("-".repeat(50));

        Logger logger = new Logger("transactions.csv");
        logger.logMessage(description, vendor, amount, "", 0, 0, 0);

        promptDeposit();


    }

    private void makeAPayment()
    {
        System.out.println();
        System.out.println("Hello, let's log in the payment: ");
        System.out.println("*".repeat(50));
        System.out.println("l) Let's go! ");
        System.out.println("X) Go back to Home Screen ");
        System.out.println("*".repeat(50));
        System.out.println();
        System.out.println("Please select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch (choice) {
            case "l":
                inputPayment();
                break;
            case "x":
                System.out.println("See you soon, Going back to Home Screen ");
                run();
                break;
            default:
                System.out.println("Invalid option, please select another one ");
        }


    }

    private void inputPayment()
    {
        while (true) {
            System.out.println();
            System.out.println("To whom did we make payment to? ");
            String vendor = userInput.nextLine();
            System.out.println("Please enter the credit/debit card number used: ");
            String cardNumber = userInput.nextLine();
            System.out.println("Enter the card expiration Month(1-12): ");
            int expirationMonth = userInput.nextInt();
            System.out.println("Enter the card expiration Year (ex. 2024): ");
            int expirationYear = userInput.nextInt();
            System.out.println("Enter the card CCV number: ");
            int ccv = userInput.nextInt();
            userInput.nextLine();

            double amount = 0;
            while (true) {
                try {
                    System.out.println("How much did we pay?");
                    amount = Double.parseDouble(userInput.nextLine().strip());
                    amount = amount * -1;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid, Please enter a valid amount.");
                }
            }

            System.out.println("Please enter a description for the payment: ");
            String description = userInput.nextLine();

            if (cardNumber.length() != 15 && cardNumber.length() != 16) {
                System.out.println("Invalid card number. It should be 15 or 16 digits.");
            }
            if (expirationMonth < 1 || expirationMonth > 12) {
                System.out.println("Invalid expiration month. It should be between 1 and 12.");
            }
            int currentYear = LocalDate.now().getYear();
            if (expirationYear < currentYear || (expirationYear == currentYear && expirationMonth < LocalDate.now().getMonthValue())) {
                System.out.println("Expiration date has passed.");
            }
            if (ccv < 100 || ccv > 9999) {
                System.out.println("Invalid CCV. It should be a 3 or 4 digit number.");
            }

            System.out.println("Log payment successful! ");

            Logger logger = new Logger("transactions.csv");
            logger.logMessage(description, vendor, amount, cardNumber, expirationMonth, expirationYear, ccv);

            makeAPayment();
        }
    }

    private void promptLedger()
    {
        System.out.println();
        System.out.println("Hello, what ledger do you want to see today? ");
        System.out.println("*".repeat(50));
        System.out.println("A) Display(ALL) entries: ");
        System.out.println("D) Display(DEPOSITS) entries only: ");
        System.out.println("P) Display(PAYMENTS) entries only: ");
        System.out.println("R) Display(Reports): ");
        System.out.println("H) Go back to the HomePage");
        System.out.println("*".repeat(50));
        System.out.println("Please make a choice Below: ");
        String choice = userInput.nextLine().strip().toLowerCase();


        switch (choice) {
            case "a":
                displayAllEntries();
                break;
            case "d":
                displayDeposits();
                break;
            case "p":
                displayPayment();
                break;
            case "r":
                promptReports();
                break;
            case "h":
                System.out.println("See you soon, Going back to Home Screen ");
                break;
            default:
                System.out.println("Invalid option, please select another one ");
        }


    }

    private void displayAllEntries()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv"))) {
            String line = reader.readLine();
            if (line == null) {
                System.out.println("No transactions Found. ");
            } else {
                System.out.println("\n Here's All the Transactions: ");
                do {
                    System.out.println(line);
                } while ((line = reader.readLine()) != null);
            }
        } catch (IOException e) {
            System.out.println("An error has occurred : " + e.getMessage());
        }
        promptLedger();
    }

    private void displayDeposits()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv"))) {
            String line = reader.readLine();
            if (line == null) {
                System.out.println("No transactions Found. ");
                return;
            }

            System.out.println("\n Here's All the Deposit Transactions: ");
            System.out.println("Date | Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(100));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
               double amount = Double.parseDouble(parts[4]);
                if (amount > 0) {
                    System.out.println(line);
                }
            }
        }catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        promptLedger();
    }

    private void displayPayment()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv"))) {
            String line = reader.readLine();
            if (line == null) {
                System.out.println("No transactions Found. ");
                return;
            }

            System.out.println("\n Here's All the Payment Transactions: ");
            System.out.println("Date| Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(100));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                double amount = Double.parseDouble(parts[4]);
                if (amount < 0) {
                    System.out.println(line);
                }
            }
        }catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        promptLedger();
    }

    private void promptReports()
    {
        System.out.println();
        System.out.println("Hello, let's look at your reports ");
        System.out.println("Which report would you like to look at: ");
        System.out.println("*".repeat(50));
        System.out.println("1) Month to Date: ");
        System.out.println("2) Previous Month: ");
        System.out.println("3) Year to Date: ");
        System.out.println("4) Previous Year: ");
        System.out.println("5) Search by Vendor: ");
        System.out.println("H) Go back to the HomePage");
        System.out.println("*".repeat(50));
        System.out.println("Please make a choice Below: ");
        String input = userInput.nextLine().strip().toLowerCase();

        try {
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    monthToDate();
                    break;
                case 2:
                    previousMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    previousYear();
                    break;
                case 5:
                    promptSearchByVendor();
                    break;
                default:
                    System.out.println("Invalid option, please select another one ");
            }
        } catch (NumberFormatException e) {
            switch (input) {
                case "h":
                    System.out.println("See you soon, Going back to Home Screen ");
                    displayHomeChoices();
                    break;
                default:
                    System.out.println("Invalid option, please select another one ");
            }
        }
    }
    private void monthToDate()
    {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv")))
        {
            reader.readLine();
            String line;

            System.out.println("\nHere is Your Month to Date Report: ");
            System.out.println("Date| Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(70));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                LocalDate transactionDate = LocalDate.parse(parts[0].trim());

                if (transactionDate.getMonthValue() == currentMonth && transactionDate.getYear() == currentYear)
                {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found)
            {
                System.out.println("No transactions found for this current Month. ");
            }
        } catch (IOException e) {
            System.out.println("AN error occurred while trying to read this report: " + e.getMessage());
        }
        promptReports();
    }

    private void previousMonth()
    {

        LocalDate currentDate = LocalDate.now();
        LocalDate lastMonthDate = currentDate.minusMonths(1);
        int lastMonth = lastMonthDate.getMonthValue();
        int lastYear = lastMonthDate.getYear();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv")))
        {
            reader.readLine();
            String line;

            System.out.println("\nHere is Your Previous Month Report: ");
            System.out.println("Date| Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(70));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                LocalDate transactionDate = LocalDate.parse(parts[0].trim());

                if (transactionDate.getMonthValue() == lastMonth && transactionDate.getYear() == lastYear)
                {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found)
            {
                System.out.println("No transactions found for previous Month. ");
            }
        } catch (IOException e) {
            System.out.println("AN error occurred while trying to read this report: " + e.getMessage());
        }
        promptReports();
    }


    private void yearToDate()
    {

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv")))
        {
            reader.readLine();
            String line;

            System.out.println("\nHere is Your Year to Date Report: ");
            System.out.println("Date| Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(70));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                LocalDate transactionDate = LocalDate.parse(parts[0].trim());

                if (transactionDate.getYear() == currentYear)
                {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found)
            {
                System.out.println("No transactions found for current Year. ");
            }
        } catch (IOException e) {
            System.out.println("AN error occurred while trying to read this report: " + e.getMessage());
        }
        promptReports();
    }


    private void previousYear()
    {

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int previousYear = currentYear - 1;
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv")))
        {
            reader.readLine();
            String line;

            System.out.println("\nHere is Your Previous Year Report: ");
            System.out.println("Date| Time| Description| Vendor| Amount| Card Number| Expiration Month| Expiration Year| CCV");
            System.out.println("-".repeat(70));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                LocalDate transactionDate = LocalDate.parse(parts[0].trim());

                if (transactionDate.getYear() == previousYear)
                {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found)
            {
                System.out.println("No transactions found for current Year. ");
            }
        } catch (IOException e) {
            System.out.println("AN error occurred while trying to read this report: " + e.getMessage());
        }
        promptReports();
    }

    private void promptSearchByVendor()
    {
        System.out.println();
        System.out.println("Hello, let's search for a specific vendor/client/company? ");
        System.out.println("*".repeat(50));
        System.out.println("Y) Yes, please! ");
        System.out.println("N) No, go back to Reports Home Page ");
        System.out.println("*".repeat(50));
        System.out.println("Please select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch (choice) {
            case "y":
                searchByVendor();
                break;
            case "n":
                System.out.println("See you soon, Going back to Reports Home Screen ");
                promptReports();
                break;
            default:
                System.out.println("Invalid option, please select another one ");
        }

    }

    private void searchByVendor()
    {
        System.out.println("\nEnter the vendor/client/company name you need to search for below: ");
        String vendorName = userInput.nextLine().trim().toLowerCase();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("files/transactions.csv")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                String vendor = parts[3].trim().toLowerCase();

                if (vendor.equals(vendorName))
                {
                    System.out.println(line);
                    found = true;
                }
            }

            if (!found)
            {
                System.out.println("No transactions found with the vendor " + vendorName + ".");
            }
        } catch (IOException e)
        {
            System.out.println("An error occurred while trying to get transactions with vendor: " + e.getMessage());
        }

        promptReports();
    }

}














