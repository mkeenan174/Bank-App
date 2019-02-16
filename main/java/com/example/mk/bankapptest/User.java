package com.example.mk.bankapptest;

import java.util.LinkedList;

public class User {
    private final String Name;
    private byte[] Password;
    private LinkedList<Account> myAccounts;
    private String Address;
    private String dateOfBirth;

    private final RSAencryption rsa;

    public User(String name, String address, String DOB, String p ) {
        this.rsa = new RSAencryption();
        this.Name = name;
        this.Password = rsa.encrpytPassword(p);;
        this.myAccounts = new LinkedList<Account>();
        this.Address = address;
        this.dateOfBirth = DOB;

    }

    public boolean passwordCheck(String p){
        System.out.println(rsa.decryptPassword(Password));
        if (p.equals(rsa.decryptPassword(Password))){
            return true;
        }
        return false;
    }


    public String getName(){
        return Name;
    }

    public LinkedList<Account> getMyAccounts(){
        return myAccounts;

    }

    public Account getMyAccount(int index){
        Account account = myAccounts.get(index);
        return account;
    }

    public Account getAccount(int number){
        for(Account account : myAccounts){
            if(number == account.getAccountNum()){
                return account;

            }
        }
        return  null;

    }


}
