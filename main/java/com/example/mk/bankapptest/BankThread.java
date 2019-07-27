package com.example.mk.bankapptest;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import static com.example.mk.bankapptest.MainActivity.createAccountLinkBtn;
import static com.example.mk.bankapptest.MainActivity.logInLinkBtn;
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
            logInLinkBtn.setVisibility(View.VISIBLE);
            createAccountLinkBtn.setVisibility(View.VISIBLE);
            }
        });
        Looper.loop();

    }
}
