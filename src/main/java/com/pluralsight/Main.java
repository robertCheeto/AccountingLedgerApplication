package com.pluralsight;

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
        displayMenu();
        char userInput = keyboard.nextLine().toLowerCase().trim().charAt(0);


    } // end of main()

    public static void displayMenu() {
        System.out.println("*****\tWelcome to Big Banks\t*****\n");
        System.out.println("Select a Menu based on the Letter\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Display Ledger");
        System.out.println("X) Close Program");
        System.out.print("Enter your choice here: ");

    } // end of displayMenu()

}
