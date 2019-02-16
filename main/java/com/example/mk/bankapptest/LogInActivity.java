package com.example.mk.bankapptest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.mk.bankapptest.BankThread.bank;

public class LogInActivity extends AppCompatActivity {
    boolean passwordCheck;
    String errorText = "Error Try again";
    String successText = "Success Welcome Back!";
    public static Handler mainHandler = new Handler();
    BankThread bankThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        bankThread = MainActivity.getBankThread();


        Button logInBtn = (Button) findViewById(R.id.LogInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = (EditText) findViewById(R.id.userNameInput);
                EditText password = (EditText) findViewById(R.id.passwordInput);
                final String n = userName.getText().toString();
                final String p = password.getText().toString();
                final TextView logInErrorText = (TextView) findViewById(R.id.logInErrorTextView);
                final Intent startIntent = new Intent(getApplicationContext(), AccountSelectionActivity.class);
                bankThread.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bank.logOn(n, p) == true) {
                            logInErrorText.setText(successText);
                            startActivity(startIntent);
                        } else {
                            logInErrorText.setText(errorText);


                        }
                    }
                });

            }

        });
    }

}
