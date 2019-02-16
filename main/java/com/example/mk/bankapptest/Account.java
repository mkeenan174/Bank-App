package com.example.mk.bankapptest;

import java.util.LinkedList;

public class Account {
    protected int accountCount = 1999890;
    protected int accountNum;
    protected double accountBalance;
    protected double minDeposit = 0;
    protected LinkedList<Transaction> myTransactions;
    protected String accountType;
    //protected static Loan loan;


    public Account(double deposit, int num){
        this.accountNum = accountCount + num;
        this.accountBalance = deposit;
        this.myTransactions = new LinkedList<Transaction>();

    }

    public void setBalance(double amount){
        this.accountBalance = accountBalance + amount;
    }


    public int getAccountNum(){
        return accountNum;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public LinkedList<Transaction> getMyTransactions() {
        return myTransactions;
    }

    public void addTransaction(Transaction transaction){
            myTransactions.add(transaction);
    }
}
