package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);


        /**
         * create home screen that allows the user to do the follow:
         * D) add deposit (prompt user for depo info) | P) make a payment (prompt user for the debit info)
         * L) ledger (display ledger screen) | X) exit program
         * all these options must save to a CSV except exit program
         */

        System.out.println("*****\tWelcome to Big Banks\t*****\n");

        while (true) {
            displayMenu();
            char userInput = keyboard.nextLine().toLowerCase().trim().charAt(0);

            switch (userInput) {
                case ('d'):
                    System.out.println("\nLoading Deposit Menu...");
                    break;
                case ('p'):
                    System.out.println("\nLoading Payment Menu...");
                    break;
                case ('l'):
                    System.out.println("\nLoading Ledger Menu...");
                    break;
                case ('x'):
                    System.out.println("\nClosing program...");
                    keyboard.close();
                    System.exit(0);
                default:
                    System.out.println("\nPlease enter a valid input.\n");
                    break;
            }
        }
    } // end of main()

    public static void displayMenu() {
        // need to call loadTransactions() here so user data is loaded when user selects menu choice
        //HashMap<String, Account> userAccount = loadTransaction();
        System.out.println("Select a Menu based on the Letter\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Display Ledger");
        System.out.println("X) Close Program");
        System.out.print("Enter your choice here: ");

    } // end of displayMenu()

    public static  HashMap<String, Account> loadTransactions() {
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


    // need to enter in HashMap and Scanner into the arguments for this method
    // need to use bufWriter to append to the file
    // need to figure out how to get the date and time properly
    // need to remember how to properly format this updated information to the csv
    public static void depositMenu(HashMap<String, Account> userAccount, Scanner keyboard) {
        System.out.println("Enter in deposit information.");

        // date should be set to the current date when the deposit is happening
        System.out.print("Enter the date (YYY-MM-dd)");
        String date = keyboard.nextLine().trim();

        // time should be set to the current time when the deposit is happening
        System.out.print("Enter the time (hh-mm-ss)");
        String time = keyboard.nextLine();

        System.out.print("Enter deposit description: ");
        String description = keyboard.nextLine().trim();

        System.out.print("Enter vendor: ");
        String vendor = keyboard.nextLine();

        System.out.print("Enter in amount: ");
        double amount = keyboard.nextDouble();
        keyboard.nextLine();

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
            bufWriter.write(String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount));
            bufWriter.newLine();
            bufWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } // end of depositMenu()

}
