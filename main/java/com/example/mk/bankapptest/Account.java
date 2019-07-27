package com.example.mk.bankapptest;

import java.time.LocalDate;
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

    public void withdrawBalance(double amount ){
        this.accountBalance = accountBalance - amount;
        addTransaction(new Transaction(-amount,"Withdrawal of finance out of account.","Withdrawal", LocalDate.now()));
    }

    public void depositBalance(double amount){
        this.accountBalance = accountBalance + amount;
        addTransaction(new Transaction(amount,"Deposit of finance into account","Deposit",LocalDate.now()));
    }


    public int getAccountNum(){
        return accountNum;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public String getFormattedAccountBalance(){
        return currencyFormatter(accountBalance);
    }

    public LinkedList<Transaction> getMyTransactions() {
        return myTransactions;
    }

    public void addTransaction(Transaction transaction){
            myTransactions.add(transaction);
    }

    public String currencyFormatter(double accountBalance){
        String S = java.text.NumberFormat.getCurrencyInstance().format(accountBalance);
        return S;
    }

}
