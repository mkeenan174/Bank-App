package com.example.mk.bankapptest;
import android.os.Handler;
import android.os.Looper;

import static com.example.mk.bankapptest.MainActivity.mainHandler;
import static com.example.mk.bankapptest.MainActivity.welcomeTextView;
public class BankThread extends Thread  {
    Handler handler;
    public static Bank bank;
    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler();
        bank = new Bank();
        bank.populateBank();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
            welcomeTextView.setText("Bank Created");
            }
        });
        Looper.loop();

    }
}
