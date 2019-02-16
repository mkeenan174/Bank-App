package com.example.mk.bankapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import static com.example.mk.bankapptest.BankThread.bank;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{
    BankThread bankThread;
    private CardView withdrawDepositOption, viewSpendingOption, transferOption, transactionOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        bankThread = MainActivity.getBankThread();

        withdrawDepositOption = (CardView) findViewById(R.id.withdraw_deposit_option);
        viewSpendingOption = (CardView) findViewById(R.id.view_spending_option);
        transferOption = (CardView) findViewById(R.id.transfer_option);
        transactionOption = (CardView) findViewById(R.id.transactions_option);

        withdrawDepositOption.setOnClickListener(this);
        viewSpendingOption.setOnClickListener(this);
        transferOption.setOnClickListener(this);
        transactionOption.setOnClickListener(this);

        final TextView accountBalance = (TextView) findViewById(R.id.accountBalanceTextView);
        Intent in = getIntent();
        //final int index = in.getIntExtra("com.example.mk.INDEX", -1);

            bankThread.handler.post(new Runnable() {
                @Override
                public void run() {
                    //bank.setLogAccount(index);
                    accountBalance.setText("€" + Double.toString(bank.getLogAccountBalance()));
                }
            });







    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()){
            case R.id.withdraw_deposit_option : i = new Intent(this, WithdrawDepositActivity.class);
                startActivity(i);
            break;

            case R.id.transfer_option : i = new Intent(this, TransferActivity.class);
                startActivity(i);
            break;


            case R.id.transactions_option : i = new Intent(this, ViewTransactionsActivity.class);
                startActivity(i);
            break;

            case R.id.view_spending_option : i = new Intent(this, ViewSpendingActivity.class);
                startActivity(i);
                break;
        }

    }
}
