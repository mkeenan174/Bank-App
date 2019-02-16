package com.example.mk.bankapptest;


import java.time.LocalDate;

public class Transaction {
    private double amount;
    private LocalDate date;
    private String description;
    private String type;

    public Transaction(double tAmount, String tDescription, String tType){
            this.amount = tAmount;
            this.description = tDescription;
            this.type = tType;
            this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
