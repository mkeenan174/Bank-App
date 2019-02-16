package com.example.mk.bankapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.mk.bankapptest.BankThread.bank;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {
    BankThread bankThread;
    String[] inputArray;
    EditText payeeNameInput ;
    EditText payeeAccNumInput;
    EditText transferInput ;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        bankThread = MainActivity.getBankThread();
        payeeNameInput = (EditText) findViewById(R.id.payee_name_input);
        payeeAccNumInput = (EditText) findViewById(R.id.payee_accnum_input);
        transferInput = (EditText) findViewById(R.id.transfer_amount_input);
        Button transferButton = (Button) findViewById(R.id.transfer_btn);
        transferButton.setOnClickListener(this);
        intent = new Intent(this,MainMenuActivity.class);




    }

    @Override
    public void onClick(View v) {
        final String nameInput = payeeNameInput.getText().toString();
        String accNumInput = payeeAccNumInput.getText().toString();
        String amountInput = transferInput.getText().toString();
        inputArray = new String[]{nameInput, accNumInput, amountInput};
        if(inputChecker(inputArray) == true){
            final double amount = Double.parseDouble(amountInput);
            final int number = Integer.parseInt(accNumInput);
            bankThread.handler.post(new Runnable() {
                @Override
                public void run() {
                bank.transfer(nameInput,number,amount);
                startActivity(intent);

                }
            });

        }

    }


    public boolean inputChecker(String[] inputInfo) {
        for (String input : inputInfo) {
            if (input.length() == 0) {
                return false;
            }

        }
        return true;
    }
}
