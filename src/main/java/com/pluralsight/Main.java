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
        System.out.println("*****\tWelcome to Big Banks\t*****");

        while (true) {
            HashMap<Integer, Account> userAccount = loadTransactions();
            displayMenu();
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
                    System.out.println("\nClosing program...");
                    keyboard.close();
                    System.exit(0);
                default:
                    System.out.println("\n*****\nPlease enter a valid input.\n*****");
                    break;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("\nSelect a Menu based on the Letter\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Display Ledger");
        System.out.println("X) Close Program");
        System.out.print("Enter your choice here: ");
    }

    public static HashMap<Integer, Account> loadTransactions() {
        HashMap<Integer, Account> userAccount = new HashMap<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
            String input;
            bufReader.readLine();

            int id = 0;
            while ((input = bufReader.readLine()) != null) {
                String[] parsedList = input.split("\\|");
                String date = parsedList[0];
                String time = parsedList[1];
                String description = parsedList[2];
                String vendor = parsedList[3];
                double amount = Double.parseDouble(parsedList[4]);
                id++;

                userAccount.put(id, new Account(date, time, description, vendor, amount, id));
            }

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userAccount;
    }

    public static String localDate() {
        LocalDate date = LocalDate.now();
        String localDate = date.toString();
        return localDate;
    }

    public static String getPreviousMonth() {
        LocalDate currentDate = LocalDate.now();
        int currentNumMonth = currentDate.getMonthValue();
        currentNumMonth = currentNumMonth - 1;
        String previousMonth;

        if (currentNumMonth < 10 && currentNumMonth > 1) {
            String tempMonth = String.valueOf(currentNumMonth);
            StringBuilder sb = new StringBuilder(tempMonth);
            sb.insert(0, "0");
            previousMonth = sb.toString();
        }
        else if (currentNumMonth == 1) {
            currentNumMonth = 12;
            previousMonth = Integer.toString(currentNumMonth);
        }
        else {
            previousMonth = Integer.toString(currentNumMonth);
        }
        return previousMonth;
    }

    public static String getPreviousYear() {
        LocalDate currentYear = LocalDate.now();
        int currentNumYear = currentYear.getYear();
        currentNumYear = currentNumYear - 1;

        String previousYear = Integer.toString(currentNumYear);

        return previousYear;
    }

    public static String localTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String localTime = time.format(format);
        return localTime;
    }

    public static void depositMenu(HashMap<Integer, Account> userAccount) {
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
            int id = userAccount.size() + 1;
            userAccount.put(id, new Account(localDate(), localTime(), depositInfo.getDescription(), depositInfo.getVendor(), depositInfo.getAmount(), depositInfo.getTransactionID()));
            try {
                BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
                bufWriter.write(String.format("%s|%s|%s|%s|%.2f|%d\n", localDate(), localTime(), description, vendor, amount, id));
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

    public static void paymentMenu(HashMap<Integer, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter the information below:");
        System.out.print("Enter payment information: ");
        String description = keyboard.nextLine().trim();

        System.out.print("Enter who the payment is for: ");
        String vendor = keyboard.nextLine().trim();

        System.out.print("Enter in amount: ");
        double amount = keyboard.nextDouble();
        keyboard.nextLine();

        for (Account paymentInfo : userAccount.values()) {
            int id = userAccount.size() + 1;
            userAccount.put(id, new Account(localDate(), localTime(), paymentInfo.getDescription(), paymentInfo.getVendor(), paymentInfo.getAmount(), paymentInfo.getTransactionID()));
            try {
                BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
                bufWriter.write(String.format("%s|%s|%s|%s|-%.2f|%d\n", localDate(), localTime(), description, vendor, amount, id));
                bufWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        System.out.println("\n*****\nYou have made a payment with the following details to your account:");
        System.out.printf("Payment Details: \"%s\" | Vendor: \"%s\" | Amount: $-%.2f\n", description, vendor, amount);
    }

    public static void displayLedgerMenu(HashMap<Integer, Account> userAccount) {
        System.out.println("Please select a ledger option: ");
        System.out.println("A) Show All Entries");
        System.out.println("D) Show Deposits");
        System.out.println("P) Show Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("Enter your choice here: ");
        ledgerMenu(userAccount);
    }

    public static void ledgerMenu(HashMap<Integer, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);
        char userInput = keyboard.nextLine().toLowerCase().trim().charAt(0);

        switch (userInput) {
            case ('a'):
                System.out.println("\nLoading all entries...");
                displayAllEntries(userAccount);
                break;
            case ('d'):
                System.out.println("\nLoading deposits...");
                displayAllDeposits(userAccount);
                break;
            case ('p'):
                System.out.println("\nLoading payments...");
                displayAllPayments(userAccount);
                break;
            case ('r'):
                System.out.println("\nLoading reports...\n");
                displayReportsMenu(userAccount);
                break;
            case ('h'):
                System.out.println("\nReturning Home...");
                displayMenu();
                break;
            default:
                System.out.println("\nPlease enter a valid entry.\n");
                displayLedgerMenu(userAccount);
        }
    }

    public static void displayAllEntries(HashMap<Integer, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount|id");
        for (Account ledgerInfo : userAccount.values()) {
            System.out.printf("%s|%s|%s|%s|$%.2f|%d", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
            System.out.println();
        }
        System.out.print("*****\n");
    }

    public static void displayAllDeposits(HashMap<Integer, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount|id");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getAmount() >= 0.01) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
                System.out.println();
            }
        }
        System.out.print("*****\n");
    }

    public static void displayAllPayments(HashMap<Integer, Account> userAccount) {

        System.out.println("\n*****\ndate|time|description|vendor|amount|id");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getAmount() <= -0.01) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
                System.out.println();
            }
        }
        System.out.print("*****\n");
    }

    public static void displayReportsMenu(HashMap<Integer, Account> userAccount) {
        System.out.println("Please select a report option: ");
        System.out.println("1) Month-To-Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year-To-Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search By Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back");
        System.out.print("Enter your choice here: ");
        reportMenu(userAccount);
    }

    public static void reportMenu(HashMap<Integer, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);
        int userInput = keyboard.nextInt();

        switch (userInput) {
            case (1):
                System.out.println("\nLoading Month-To-Date Report...");
                displayMTDReport(userAccount);
                break;
            case (2):
                System.out.println("\nLoading Previous Month Report...");
                displayPreviousMonthReport(userAccount);
                break;
            case (3):
                System.out.println("\nLoading Year-To-Date Report...");
                displayYTDReport(userAccount);
                break;
            case (4):
                System.out.println("\nLoading Previous Year Report...");
                displayPreviousYearReport(userAccount);
                break;
            case (5):
                System.out.println("\nLoading \"Entries by Vendor\" Menu...");
                searchByVendor(userAccount);
                break;
            case (6):
                System.out.println("\nLoading Custom Search...\n");
                customSearch(userAccount);
                break;
            case (0):
                System.out.println("\nReturning Home...");
                displayLedgerMenu(userAccount);
                break;
            default:
                System.out.println("\nPlease enter a valid number.");
                displayReportsMenu(userAccount);
        }
    }

    public static void displayMTDReport(HashMap<Integer, Account> userAccount) {
        LocalDate currentDate = LocalDate.now();
        String currentMonth = String.valueOf(currentDate.getMonthValue());
        String currentYear = String.valueOf(currentDate.getYear());
        System.out.println("\n*****\ndate|time|description|vendor|amount|id");

        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getDate().contains(currentYear + "-" + currentMonth)) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d\n", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
            }
        }
        System.out.print("*****\n");
    }

    public static void displayPreviousMonthReport(HashMap<Integer, Account> userAccount) {
        LocalDate currentDate = LocalDate.now();
        String currentYear = String.valueOf(currentDate.getYear());
        System.out.println("\n*****\ndate|time|description|vendor|amount|id");

        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getDate().contains(currentYear + "-" + getPreviousMonth())) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d\n", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
            }
        }
        System.out.print("*****\n");
    }

    public static void displayYTDReport(HashMap<Integer, Account> userAccount) {
        LocalDate currentDate = LocalDate.now();
        String currentYear = String.valueOf(currentDate.getYear());
        System.out.println("\n*****\ndate|time|description|vendor|amount|id");

        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getDate().contains(currentYear)) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d\n", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
            }
        }
        System.out.print("*****\n");
    }

    public static void displayPreviousYearReport(HashMap<Integer, Account> userAccount) {
        System.out.println("\n*****\ndate|time|description|vendor|amount|id");

        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getDate().contains(getPreviousYear())) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d\n", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
            }
        }
        System.out.print("*****\n");
    }

    public static void searchByVendor(HashMap<Integer, Account> userAccount) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\nPlease enter a vendor you would like to search for: ");
        String userInput = keyboard.nextLine().toLowerCase().trim();

        System.out.println("\n*****\ndate|time|description|vendor|amount|id");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getVendor().toLowerCase().trim().contains(userInput)) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
                System.out.println();
            }
        }
        System.out.print("*****\n");
    }

    public static void customSearch(HashMap<Integer, Account> userAccount) {
        System.out.println("\n***** THIS METHOD IS STILL A WIP *****");
        Scanner keyboard = new Scanner(System.in);

        System.out.print("\nPlease enter a starting date for the transaction(s) you want to search for (YYYY-MM-DD): ");
        String startDate = keyboard.nextLine().toLowerCase().trim();

        System.out.print("\nPlease enter an end date for the transaction(s) you want to search for (YYYY-MM-DD): ");
        String endDate = keyboard.nextLine().toLowerCase().trim();

        System.out.print("\nPlease enter a description the transaction contains: ");
        String description = keyboard.nextLine().toLowerCase().trim();

        System.out.print("\nPlease enter a vendor you would like to search for: ");
        String vendor = keyboard.nextLine().toLowerCase().trim();

        System.out.print("\nPlease enter an amount the transaction contains: $");
        double amount;
        amount = keyboard.nextDouble();
        keyboard.nextLine();

        System.out.println("Searching for transactions that match criteria...");

        // when "nothing" is entered for description or vendor, the program does not know how to account for that
        // program will also not take in the user pressing "enter" in for the amount

        System.out.println("\n*****\ndate|time|description|vendor|amount|id");
        for (Account ledgerInfo : userAccount.values()) {
            if (ledgerInfo.getAmount() == amount || ledgerInfo.getVendor().toLowerCase().trim().contains(vendor) || ledgerInfo.getDescription().toLowerCase().trim().contains(description)) {
                System.out.printf("%s|%s|%s|%s|$%.2f|%d", ledgerInfo.getDate(), ledgerInfo.getTime(), ledgerInfo.getDescription(), ledgerInfo.getVendor(), ledgerInfo.getAmount(), ledgerInfo.getTransactionID());
                System.out.println();
            }
        }
        System.out.print("*****\n");
    }

}