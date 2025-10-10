package com.pluralsight;

public class Account {
    private String description, vendor, date, time;
    private double amount;

    public Account(String description, String vendor, String date, String time, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // create getters for other reports in the ledger
    // MtD, Previous 30 days, YtD, Previous Year

}
