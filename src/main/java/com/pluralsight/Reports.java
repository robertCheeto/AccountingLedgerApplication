package com.pluralsight;

public class Reports {
    private  String date;
    private int months, days, year;
    private double amount;

    public Reports(String date, int months, int days, int year, double amount) {
        this.date = date;
        this.months = months;
        this.days = days;
        this.year = year;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }

    public int getYear() {
        return year;
    }

    public double getAmount() {
        return amount;
    }
}
