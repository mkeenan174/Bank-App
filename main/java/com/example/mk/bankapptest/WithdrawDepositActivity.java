package com.example.mk.bankapptest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.mk.bankapptest.BankThread.bank;

public class WithdrawDepositActivity extends AppCompatActivity implements View.OnClickListener{
    EditText amountInput;
    Button depositBtn;
    Button withdrawBtn;
    BankThread bankThread;
    Intent intent;
    Context mContext = this;
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
        if(amount > 0){
            switch (v.getId()) {
                case R.id.withdraw_button:
                    if(amount < bank.getLogAccountBalance()) {
                        bankThread.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bank.withdraw(amount);
                                startActivity(intent);
                                Toast.makeText(mContext, "Withdrawal done.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(amount >= bank.getLogAccountBalance()){
                        Toast.makeText(mContext,"Error Cannot preform a transaction on an amount greater or equal to account balance",Toast.LENGTH_LONG).show();
                        amountInput.setTextColor(Color.RED);
                }
                    break;
                case R.id.deposit_button:
                    bankThread.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bank.deposit(amount);
                            Toast.makeText(mContext, "Deposit done.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }else if (amount <= 0 ){
            Toast.makeText(mContext,"Error Cannot preform a transaction on an amount of 0 or less.",Toast.LENGTH_LONG).show();
            amountInput.setTextColor(Color.RED);
        }

    }
}
