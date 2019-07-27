package com.example.mk.bankapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.mk.bankapptest.BankThread.bank;

public class OpenNewAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int accountChoice;
    EditText depositInput;
    int minDepo;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_new_account);
        final BankThread bankThread = MainActivity.getBankThread();
        //Account selection spinner:
        Spinner spinner = findViewById(R.id.open_account_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        depositInput = (EditText) findViewById(R.id.open_deposit_input);
        Button openAccountBtn = (Button) findViewById(R.id.open_account_btn);
        intent = new Intent(this, AccountSelectionActivity.class);
        openAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tempDeposit = depositInput.getText().toString();
                System.out.println(tempDeposit);
                int i = Integer.parseInt(tempDeposit);
                if(i > minDepo){
                    bankThread.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            double accountDeposit = Bank.amountInput(tempDeposit);
                            bank.createAccount(bank.getLogUser(),accountChoice,accountDeposit);
                            bank.clearAccount();
                        }
                    });
                    startActivity(intent);

                }else{

                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        accountChoice = position;
        String studentMin = "Min deposit of €50";
        String currentMin = "Min deposit of €200";
        switch (position) {
            case 0:
                depositInput.setHint(studentMin);
                minDepo = 50;
                break;

            case 1:
                depositInput.setHint(currentMin);
                minDepo = 200;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
