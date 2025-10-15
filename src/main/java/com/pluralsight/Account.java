package com.pluralsight;

public class Account {
    private String description, vendor, date, time;
    private double amount;
    private int transactionID;

    public Account(String date, String time, String description, String vendor, double amount, int transactionID) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.transactionID = transactionID;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public int getTransactionID() {
        return transactionID;
    }

}
