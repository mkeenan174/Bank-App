package com.example.mk.bankapptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import static com.example.mk.bankapptest.BankThread.bank;

public class MainActivity extends AppCompatActivity {
    public  static  Handler mainHandler = new Handler();
    public static TextView welcomeTextView;
    public static BankThread bankThread;
    public static Button logInLinkBtn;
    public static Button createAccountLinkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bankThread = new BankThread();
        bankThread.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeTextView = findViewById(R.id.welcomeTextView);
        logInLinkBtn = (Button) findViewById(R.id.logInLinkBtn);
        logInLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(startIntent);
            }
        });

        createAccountLinkBtn = (Button) findViewById(R.id.createAccountLinkBtn);
        createAccountLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent startIntent = new Intent(getApplicationContext(),CreateAccountActivity.class);
                startActivity(startIntent);
            }
        });

    }


    public static BankThread getBankThread(){
        return bankThread;

    }
}



