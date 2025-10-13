package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);


        /**
         * TO-DO:
         * /determine way to sort the transactions.csv in reverse chronological order
         * (most recent transactions at the top and oldest at the bottom)
         * /find a way to display that information to the user on the screen
         *
         * /began working on displayMTDReport() / need to finish that method and make the other report methods
         * /those methods are the previous month, YtD, previous year, and search by vendor
         * /work on finishing the project by adding in "safety nets" and other QOL features
         * /figure out how to download this updated version of the project to the YU laptop
         */

        System.out.println("*****\tWelcome to Big Banks\t*****");

        while (true) {
            displayMenu();
            HashMap<String, Account> userAccount = loadTransactions();
            char userInput = keyboard.nextLine().toLowerCase().trim().charAt(0);

            switch (userInput) {
                case ('d'):
                    System.out.println("\nLoading Deposit Menu...\n");
                    depositMenu(userAccount);
                    break;
                case ('p'):
                    System.out.println("\nLoading Payment Menu...\n");
                    paymentMenu(userAccount);
                    break;
                case ('l'):
                    System.out.println("\nLoading Ledger Menu...\n");
                    displayLedgerMenu(userAccount);
                    break;
                case ('x'):
                    System.out.println("\nClosing program...\n");
                    keyboard.close();
                    System.exit(0);
                default:
                    System.out.println("\n*****\nPlease enter a valid input.\n*****");
                    break;
            }
        }
    } // end of main()

    public static void displayMenu() {
        System.out.println("\nSelect a Menu based on the Letter\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Display Ledger");
        System.out.println("X) Close Program");
        System.out.print("Enter your choice here: ");
    }

    public static HashMap<String, Account> loadTransactions() {
        HashMap<String, Account> userAccount = new HashMap<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
            String input;
            bufReader.readLine();

            while ((input = bufReader.readLine()) != null) {
                String[] parsedList = input.split("\\|");
                String date = parsedList[0];
                String time = parsedList[1];
                String description = parsedList[2];
                String vendor = parsedList[3];
                double amount = Double.parseDouble(parsedList[4]);

                userAccount.put(date, new Account(date, time, description, vendor, amount));
            }

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userAccount;
    } // end of loadTransactions()

    public static String localDate() {
        LocalDate date = LocalDate.now();
        String localDate = date.toString();
        return localDate;
    }

    public static String localTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String localTime = time.format(format);
        return localTime;
    }

    public static void depositMenu(HashMap<String, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter in deposit information.");
        System.out.print("Enter deposit description: ");
        String description = keyboard.nextLine().trim();

        System.out.print("Enter vendor: ");
        String vendor = keyboard.nextLine().trim();

        System.out.print("Enter in amount: ");
        double amount = keyboard.nextDouble();
        keyboard.nextLine();

        for (Account depositInfo : userAccount.values()) {
            userAccount.put(localDate(), new Account(localDate(), localTime(), depositInfo.getDescription(), depositInfo.getVendor(), depositInfo.getAmount()));
            try {
                BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
                bufWriter.write(String.format("%s|%s|%s|%s|%.2f\n", localDate(), localTime(), description, vendor, amount));
                bufWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        System.out.println("\n*****\nYou have made a deposit with the following details to your account:");
        System.out.printf("Deposit Description: \"%s\" | Vendor: \"%s\" | Amount: $%.2f\n*****\n", description, vendor, amount);
    }

    // ask the user for debit information
    // subtract from the total balance
    // create a new line in their transaction with debit info
    public static void paymentMenu(HashMap<String, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter the information below:");
        System.out.print("Enter payment information: ");
        String description = keyboard.nextLine().trim();

        System.out.print("Enter who the payment is for: ");
        String vendor = keyboard.nextLine().trim();

        System.out.print("Enter in amount: ");
        double amount = keyboard.nextDouble();
        keyboard.nextLine();

        for (Account depositInfo : userAccount.values()) {
            userAccount.put(localDate(), new Account(localDate(), localTime(), depositInfo.getDescription(), depositInfo.getVendor(), depositInfo.getAmount()));
            try {
                BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
                bufWriter.write(String.format("%s|%s|%s|%s|-%.2f\n", localDate(), localTime(), description, vendor, amount));
                bufWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        System.out.println("\n*****\nYou have made a payment with the following details to your account:");
        System.out.printf("Payment Details: \"%s\" | Vendor: \"%s\" | Amount: $-%.2f\n", description, vendor, amount);
        System.out.printf("Total Account Balance: $%.2f\n*****\n", amount);
    }

    public static void displayLedgerMenu(HashMap<String, Account> userAccount) {
        System.out.println("Please select a ledger option: ");
        System.out.println("A) Show All Entries");
        System.out.println("D) Show Deposits");
        System.out.println("P) Show Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("Enter your choice here: ");
        ledgerMenu(userAccount);
    } // end of ledgerMenu()

    // need to determine if this is how I want to do this method or move the below to main, etc.
    //
    public static void ledgerMenu(HashMap<String, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);
        char userInput = keyboard.nextLine().toLowerCase().trim().charAt(0);

        switch (userInput) {
            case ('a'):
                System.out.println("Loading all entries...");
                displayAllEntries(userAccount);
                break;
            case ('d'):
                System.out.println("Loading deposits...");
                displayAllDeposits(userAccount);
                break;
            case ('p'):
                System.out.println("Loading payments...");
                displayAllPayments(userAccount);
                break;
            case ('r'):
                System.out.println("Loading reports...");
                displayReportsMenu(userAccount);
                break;
            case ('h'):
                System.out.println("Returning Home...");
                break;
            default:
                System.out.println("Please enter a valid entry.");
        }
    } // end of ledgerMenu()

    public static void displayAllEntries(HashMap<String, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount");
        for (Account ledgerInfo : userAccount.values()) {
            System.out.printf("%s|%s|%s|%s|$%.2f", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount());
            System.out.println();
        }
        System.out.println("end of ledger. returning back to ledger menu");
        // need to add a "press enter to return home" option

    } // end of displayAllEntries()

    public static void displayAllDeposits(HashMap<String, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getAmount() >= 0.01) {
                System.out.printf("%s|%s|%s|%s|$%.2f", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount());
                System.out.println();
            }
        }
        System.out.println("end of ledger. returning back to ledger menu");
        // need to add a "press enter to return home" option

    } // end of displayAllDeposits()

    public static void displayAllPayments(HashMap<String, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getAmount() <= -0.01) {
                System.out.printf("%s|%s|%s|%s|$%.2f", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount());
                System.out.println();
            }
        }
        System.out.println("end of ledger. returning back to ledger menu");
        // need to add a "press enter to return home" option

    } // end of displayAllPayments()

    public static void displayReportsMenu(HashMap<String, Account> userAccount) {
        System.out.println("Please select a report option: ");
        System.out.println("1) Month-To-Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year-To-Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search By Vendor");
        System.out.println("0) Back");
        System.out.print("Enter your choice here: ");
        reportMenu(userAccount);

    }


    public static void reportMenu(HashMap<String, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);
        int userInput = keyboard.nextInt();

        switch (userInput) {
            case (1):
                System.out.println("Loading Month-To-Date Report...");
                displayMTDReport(userAccount);
                break;
            case (2):
                System.out.println("Loading Previous Month Report...");
                break;
            case (3):
                System.out.println("Loading Year-To-Date Report...");
                break;
            case (4):
                System.out.println("Loading Previous Year Report...");
                break;
            case (5):
                System.out.println("Loading \"Entries by Vendor\" Menu...");
                break;
            case (6):
                System.out.println("Returning Home...");
                break;
            default:
                System.out.println("Please enter a valid number.");
        }
    }

    public static void displayMTDReport(HashMap<String, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getDate().contains(localDate())) {
                System.out.printf("%s|%s|%s|%s|$%.2f", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount());
                System.out.println();
            }
        }
        System.out.println("end of ledger. returning back to ledger menu");
        // need to add a "press enter to return home" option

    } // end of displayAllEntries()


}
