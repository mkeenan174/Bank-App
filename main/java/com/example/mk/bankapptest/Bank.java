package com.example.mk.bankapptest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Bank {
    private static User logUser = null;
    private static Account logAccount = null;
    private String bankName;
    private static LinkedList<User> bankUsers;
    private int accountCounter = 0;
    DecimalFormat df = new DecimalFormat("0.00");

    public Bank(){
        bankUsers = new LinkedList<User>();
    }

    public void populateBank() {
        User user1 = new User("Derek", "1 Dublin Street Monaghan","05/09/1994","password");
        bankUsers.add(user1);
        createAccount(user1,0,75);
        createAccount(user1,1,800);
        createUser("John","3 The Diamond Monaghan","07/10/1993","password1",1,345);
    }

    public void createAccount(User holder, int accountType, double deposit ){
        if (holder != null){
            Account account;
            switch(accountType){
                case 0:
                    if(deposit > 50){
                        account = new StudentAccount(deposit, accountCounter);
                        accountCounter++;
                        holder.getMyAccounts().add(account);
                    }
                    break;

                case 1:
                    if(deposit > 200){
                        account = new CurrentAccount(deposit,accountCounter);
                        accountCounter++;
                        holder.getMyAccounts().add(account);

                    }
                    break;

            }

    }

    }

    public void createUser(String n, String a, String d, String p, int at, double deposit) {
        User user;
        System.out.print(p);
        bankUsers.add(user = new User(n, a, d, p));
        System.out.println(n + " welcome to the bank your account has been created with us!");
        createAccount(user,at,deposit);
    }

        public boolean logOn(String n, String p){
        if(getUser(n) != null){
           logUser = getUser(n);
           if(logUser.passwordCheck(p) == true){
               return true;
           }else{
               logUser = null;
               return false;
           }

        }
        return false;
  }


    public static User getUser(String n){
        for(User user : bankUsers){
            if(n.equals(user.getName())){
                return user;
            }
        }
        return null;
    }
    public static double amountInput(String input){
        double amountInput =0;


            try{
                amountInput = Double.parseDouble(input);

            }
            catch(NumberFormatException e){
                //System.out.println("Error invalid input, use numbers only! Try again");
            }

        return amountInput;

    }

    public static boolean amountInputChecker(String input){
        double amountInput ;
        boolean isFormatted = false;

            try{
                amountInput = Double.parseDouble(input);
                isFormatted = true;
            }
            catch(NumberFormatException e){
                //System.out.println("Error invalid input, use numbers only! Try again");
            }

        return isFormatted;

    }


   public String[] getUserAccTypes(){
        String[] accTypes = new String[logUser.getMyAccounts().size()];
        int i= 0;
        for(Account account: logUser.getMyAccounts()){
            if(account.getClass() == StudentAccount.class){
               accTypes[i] = "Student Account";
               i++;
            }else{
                accTypes[i] = "Current Account";
                i++;
            }
        }
        return accTypes;
   }

    public String[] getUserAccBalance() {
        String[] accBalances = new String[logUser.getMyAccounts().size()];
        double bal;
        int i = 0;
        for (Account account : logUser.getMyAccounts()) {
             bal = account.getAccountBalance();
            accBalances[i] = "€ " + Double.toString(bal);
            i++;
        }
        return accBalances;
    }

    public String[] getUserAccNum() {
        String[] accBalances = new String[logUser.getMyAccounts().size()];
        int num;
        int i = 0;
        for (Account account : logUser.getMyAccounts()) {
            num = account.getAccountNum();
            accBalances[i] = "No: " + Integer.toString(num);
            i++;
        }
        return accBalances;
    }


  public User getLogUser(){
        return logUser;
  }

    public void setLogAccount(int index ){
        logAccount = logUser.getMyAccount(index);
    }


    public double getLogAccountBalance(){
        Double bal = logAccount.getAccountBalance();
        bal = round(bal,2);
         return bal;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    public static void  transfer(String payeeName, int accountNumber, double amount){
        User payee = getUser(payeeName);
        Account payeeAccount = payee.getAccount(accountNumber);
        String name = logUser.getName();
        payeeAccount.setBalance(amount);
        logAccount.setBalance(-amount);
        logAccount.addTransaction(new Transaction(-amount,"Transfer to "+ payeeName,"Transfer"));
        payeeAccount.addTransaction(new Transaction(amount,"Transfer from "+ name,"Transfer"));
    }


    public static void withdraw(double amount){

        logAccount.setBalance(amount);
    }

    public static void deposit(double amount){
        logAccount.setBalance(amount);
        logAccount.addTransaction(new Transaction(amount,"Deposit of finance into account","Deposit"));
    }
}



