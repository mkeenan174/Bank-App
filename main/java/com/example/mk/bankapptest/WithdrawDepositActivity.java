package com.example.mk.bankapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.mk.bankapptest.BankThread.bank;

public class WithdrawDepositActivity extends AppCompatActivity implements View.OnClickListener{
    EditText amountInput;
    Button depositBtn;
    Button withdrawBtn;
    BankThread bankThread;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);
        bankThread = MainActivity.getBankThread();
        amountInput = (EditText) findViewById(R.id.amount_input);
        depositBtn = (Button) findViewById(R.id.deposit_button);
        withdrawBtn = (Button) findViewById(R.id.withdraw_button);
        withdrawBtn.setOnClickListener(this);
        depositBtn.setOnClickListener(this);
        intent = new Intent(this,MainMenuActivity.class);


    }

    @Override
    public void onClick(View v) {
        String input = amountInput.getText().toString();
        final double amount = Double.parseDouble(input);


        switch (v.getId()){
            case R.id.withdraw_button:
                bankThread.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bank.deposit(-amount);
                        startActivity(intent);
                    }
                });

            case R.id.deposit_button:
                bankThread.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bank.deposit(amount);
                        startActivity(intent);
                    }
                });

        }

    }
}
