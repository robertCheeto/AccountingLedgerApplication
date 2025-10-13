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

// test comment
        /**
         * TO-DO:
         * /determine way to sort the transactions.csv in reverse chronological order
         * (most recent transactions at the top and oldest at the bottom)
         * /find a way to display that information to the user on the screen
         * /work on adding different menus for the ledger and displaying them
         * /need to find way to tag transactions as deposits/payments
         * /need to generate the various reports for the ledger
         * also need to add a way for the user to input what ledger they want to see
         * after they select the report option
         *
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
        System.out.println("A) Add Entries");
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
                System.out.println("Loading entries...");
                break;
            case ('d'):
                System.out.println("Loading deposits...");
                break;
            case ('p'):
                System.out.println("Loading payments...");
                break;
            case ('r'):
                System.out.println("Loading reports...");
            case ('h'):
                System.out.println("Returning Home...");
                break;
            default:
                System.out.println("Please enter a valid entry.");
                break;
        }
    }

}
