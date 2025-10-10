package com.pluralsight;

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
        System.out.println("Select a Menu based on the Letter\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Display Ledger");
        System.out.println("X) Close Program");
        System.out.print("Enter your choice here: ");

    } // end of displayMenu()

    public static void loadTransactions() {
        //HashMap<>
    } // end of loadTransactions()

    public static void depositMenu() {


    } // end of depositMenu()

}
