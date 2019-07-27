package com.example.mk.bankapptest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

import static com.example.mk.bankapptest.BankThread.bank;
import static com.example.mk.bankapptest.R.id.createPasswordInput;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText depositInput;
    int accountChoice;
    BankThread bankThread;
    String[] inputArray;
    int minDepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        bankThread = MainActivity.getBankThread();


        //Input fields:
        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText addressInput = (EditText) findViewById(R.id.addressInput);
        final EditText dobInput = (EditText) findViewById(R.id.DOBInput);
        final EditText passwordInput = (EditText) findViewById(createPasswordInput);
        depositInput = (EditText) findViewById(R.id.depositInput);

        //Account selection spinner:
        Spinner spinner = findViewById(R.id.accountSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView errorTextView = (TextView) findViewById(R.id.errorTextAccountCreate);
                final Intent startIntent = new Intent(getApplicationContext(), AccountSelectionActivity.class);

                //Setting Stings:
                final String name = nameInput.getText().toString();
                final String address = addressInput.getText().toString();
                final String DOB = dobInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String deposit = depositInput.getText().toString();

                //Using an Array to check if any input fields are left as null no prevent a crash.
                inputArray = new String[]{name, address, DOB, password, deposit};
                if (inputChecker(inputArray) == true) {

                    bankThread.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            double accountDeposit = Bank.amountInput(deposit);
                            int i = Integer.parseInt(deposit);
                            if (i >= minDepo) {
                                bank.createUser(name, address, DOB, password, accountChoice, accountDeposit);
                                errorTextView.setText("Success account created!");
                                startActivity(startIntent);


                            } else {
                                errorTextView.setText("Error: Insufficient deposit amount! ");
                                errorTextView.setTextColor(Color.RED);
                            }


                        }
                    });
                } else {
                    errorTextView.setText("Error: Please fill out all fields!");
                    errorTextView.setTextColor(Color.RED);
                }
            }
        });

    }

    //Item selection behavior for account selection spinner:
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

    //Method for checking through the inputArray to for empty Strings:
    public boolean inputChecker(String[] inputInfo) {
        for (String input : inputInfo) {
            if (input.length() == 0) {
                return false;
            }

        }
        return true;
    }
}
