package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public static void depositMenu() {


    } // end of depositMenu()

}
