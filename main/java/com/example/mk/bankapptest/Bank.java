package com.example.mk.bankapptest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Bank {
    private static User logUser = null;
    private static Account logAccount = null;
    private String bankName;
    private static LinkedList<User> bankUsers;
    private int accountCounter = 0;
    DecimalFormat df = new DecimalFormat("0.00");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Bank(){
        bankUsers = new LinkedList<User>();
    }

    public void populateBank() {
        User user1 = new User("Derek", "1 Dublin Street Monaghan","05/09/1994","password");
        bankUsers.add(user1);
        createAccount(user1,0,75);
        createAccount(user1,1,800);
        Account account = user1.getMyAccount(0);
        account.addTransaction(new Transaction(200,"Debug deposit","Deposit",LocalDate.now().minusDays(4)));
        account.addTransaction(new Transaction(-445,"Debug withdrawal","Withdrawal",LocalDate.now().minusDays(2)));
        account.addTransaction(new Transaction(-44,"Debug withdrawal","Withdrawal",LocalDate.now().minusDays(1)));
        account.addTransaction(new Transaction(50,"Debug deposit","Deposit",LocalDate.now()));
        account.addTransaction(new Transaction(100,"Debug deposit","Deposit",LocalDate.now()));
        account.addTransaction(new Transaction(-134,"Debug deposit","Deposit",LocalDate.now()));
        account.addTransaction(new Transaction(109,"Debug deposit","Deposit",LocalDate.now()));
        account.addTransaction(new Transaction(-36,"Debug deposit","Deposit",LocalDate.now()));
        account = null;
        account = user1.getMyAccount(1);
        account.addTransaction(new Transaction(50,"Debug transfer","Transfer",LocalDate.now().minusDays(1)));
        createUser("John","3 The Diamond Monaghan","07/10/1993","password1",1,345);
    }

    /**
     * Creates either a student or current account based on the account type passed.
     * The account will be added to the holder's list of accounts.
     * @param holder
     * @param accountType
     * @param deposit
     */
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

    /**
     * Creates a user and adds them to the bank's list of users.
     * Then calls the "createAccount" method.
     * Then sets the created user as the "logUser".
     * @param name
     * @param address
     * @param date
     * @param password
     * @param at
     * @param deposit
     */
    public void createUser(String name , String address, String date, String password, int at, double deposit) {
        User user;
        System.out.print(password);
        bankUsers.add(user = new User(name, address, date, password));
        System.out.println(name + " welcome to the bank your account has been created with us!");
        createAccount(user,at,deposit);
        logUser = getUser(name);
    }


    /**
     * logon checks the entered password against  the stored one and returns true if it matches or false if it does not.
     * @param n
     * @param p
     * @return true false
     */
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


  public boolean passwordCheck(String p){
      if(logUser.passwordCheck(p) == true){
          return true;}

          return false;

  }


    /**
     * return a user with the matching name of the parameter.
     * @param n
     * @return
     */
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

    /**
     * returns a string array of the logUser's account types.
     * @return
     */
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

    /**
     * Returns a string array of the logUser's balance's.
     * @return
     */
    public String[] getUserAccBalance() {
        String[] accBalances = new String[logUser.getMyAccounts().size()];
        String bal;
        int i = 0;
        for (Account account : logUser.getMyAccounts()) {
             bal = account.getFormattedAccountBalance();
            accBalances[i] = bal;
            i++;
        }
        return accBalances;
    }

    /**
     * Returns a string array of the logUser's account numbers.
     * @return
     */
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

    /**
     * Returns a string array of the logAccounts transaction types .
     * @return
     */
    public String[] getAccTranTypes() {
        String[] transTypes = new String[logAccount.getMyTransactions().size()];
        String type;
        int i = 0;
        for (Transaction transaction : logAccount.getMyTransactions()) {
            type = transaction.getType();
            transTypes[i] = type;
            i++;
        }
        return transTypes;
    }

    /**
     * Returns a string array of the logAccounts transaction descriptions.
     * @return
     */
    public String[] getAccTranDesc() {
        String[] transDesc = new String[logAccount.getMyTransactions().size()];
        String desc;
        int i = 0;
        for (Transaction transaction : logAccount.getMyTransactions()) {
            desc = transaction.getDescription();
            transDesc[i] = desc;
            i++;
        }
        return transDesc;
    }

    /**
     *  Returns a string array of the logAccounts transaction amounts.
     * @return
     */
    public String[] getAccTranAmount() {
        String[] transTypes = new String[logAccount.getMyTransactions().size()];
        double amount;
        int i = 0;
        for (Transaction transaction : logAccount.getMyTransactions()) {
            amount = transaction.getAmount();
            transTypes[i] = Double.toString(amount);
            i++;
        }
        return transTypes;
    }

    /**
     *  Returns a string array of the logAccounts transaction dates.
     * @return
     */
    public String[] getAccTranDate() {
        String[] transDates = new String[logAccount.getMyTransactions().size()];
        LocalDate date;
        int i = 0;
        for (Transaction transaction : logAccount.getMyTransactions()) {
            date = transaction.getDate();
            transDates[i] = date.format(dateFormatter);

            i++;
        }
        return transDates;
    }

    /**
     * Returns the set logUser.
     * @return logUser
     */
  public User getLogUser(){
        return logUser;
  }


    /**
     * Sets the logAccount.
     * @param index
     */
    public void setLogAccount(int index ){
        logAccount = logUser.getMyAccount(index);
    }

    /**
     * Returns the logAccount's balance.
     * @return logAccount.getAccountBalance()
     */
    public Double getLogAccountBalance(){
        Double bal = logAccount.getAccountBalance();
        return bal;
    }

    public String getFormattedLogAccountBalance(){
        String bal = logAccount.getFormattedAccountBalance();
        return bal;
    }

    /**
     *
     * @param payeeName
     * @param accountNumber
     * @param amount
     */
    public static void  transfer(String payeeName, int accountNumber, double amount){
        User payee = getUser(payeeName);
        Account payeeAccount = payee.getAccount(accountNumber);
        String name = logUser.getName();
        payeeAccount.setBalance(amount);
        logAccount.setBalance(-amount);
        logAccount.addTransaction(new Transaction(-amount,"Transfer to "+ payeeName,"Transfer",LocalDate.now()));
        payeeAccount.addTransaction(new Transaction(amount,"Transfer from "+ name,"Transfer",LocalDate.now()));
    }


    /**
     * Withdraws a given amount from the logAccount
     * @param amount
     */
    public static void withdraw(double amount){
        logAccount.withdrawBalance(amount);
    }

    /**
     * Deposits a given amount into the logAccount
     * @param amount
     */
    public static void deposit(double amount){
       logAccount.depositBalance(amount);
    }

    /**
     * Sets the logAccount and logUser to null
     */
    public void logOut(){
        logAccount = null;
        clearAccount();
    }

    /**
     * Sets the logAccount to null
     */
    public void clearAccount(){
        logAccount = null;
    }

    /**
     * Returns the name of the logUser
     * @return logUser.getName()
     */
    public static String getLogName(){
        return logUser.getName();
    }

    /**
     * Returns the logUser's Email
     * @return logUser.getAddress
     */
    public static String getLogEmail(){
        return logUser.getAddress();
    }
}



