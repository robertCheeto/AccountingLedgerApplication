package com.pluralsight;

public class Account {
    private String description, vendor, date, time;
    private double amount;

    public Account(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
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


    // create getters for other reports in the ledger
    // MtD, Previous 30 days, YtD, Previous Year

}
