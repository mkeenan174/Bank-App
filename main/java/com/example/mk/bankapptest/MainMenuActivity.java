package com.example.mk.bankapptest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.mk.bankapptest.BankThread.bank;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener,  NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnDismissListener{
    static BankThread bankThread;



    String [] defaultStringArray = {"default"};
    private CardView withdrawDepositOption, viewSpendingOption, transferOption, transactionOption;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static TextView navName;
    static TextView navEmail;
    static TextView accountBalance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        bankThread = MainActivity.getBankThread();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navName = (TextView) headerView.findViewById(R.id.nav_name);
        navEmail = (TextView) headerView.findViewById(R.id.nav_email);
        navigationView.setNavigationItemSelectedListener(this);

        withdrawDepositOption = (CardView) findViewById(R.id.withdraw_deposit_option);
        viewSpendingOption = (CardView) findViewById(R.id.view_spending_option);
        transferOption = (CardView) findViewById(R.id.transfer_option);
        transactionOption = (CardView) findViewById(R.id.transactions_option);

        withdrawDepositOption.setOnClickListener(this);
        viewSpendingOption.setOnClickListener(this);
        transferOption.setOnClickListener(this);
        transactionOption.setOnClickListener(this);

        accountBalance = (TextView) findViewById(R.id.accountBalanceTextView);

            bankThread.handler.post(new Runnable() {
                @Override
                public void run() {
                    //bank.setLogAccount(index);
                    accountBalance.setText(bank.getFormattedLogAccountBalance());
                    navName.setText(bank.getLogName());
                    navEmail.setText(bank.getLogEmail());
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


    public boolean transactionCheck(String[] types, String [] dates, String[] descs, String[] amounts){
        if(types != null || dates != null || descs != null || amounts != null){
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.change_email:
                Toast.makeText(this,"Change email",Toast.LENGTH_SHORT).show();
                openChangeEmailDialog();
            break;

            case R.id.change_account:
                Toast.makeText(this,"Change Account",Toast.LENGTH_SHORT).show();
                bankThread.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bank.clearAccount();
                    }
                });
                i = new Intent(this, AccountSelectionActivity.class);
                startActivity(i);
                break;
            case R.id.open_account:
            Toast.makeText(this,"Open Account",Toast.LENGTH_SHORT).show();
            i = new Intent(this,OpenNewAccountActivity.class);
            startActivity(i);
            break;

            case R.id.log_out:
                openDialog();
               // Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();

               // i = new Intent(this, MainActivity.class);
                //startActivity(i);
                break;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(){
        LogoutDialogA logoutDialogA = new LogoutDialogA();
        logoutDialogA.show(getSupportFragmentManager(),"Logout Dialog");
    }

    public void openChangeEmailDialog(){
        EmailChangeDialog emailChangeDialog = new EmailChangeDialog();
        emailChangeDialog.show(getSupportFragmentManager(),"Change Email Dialog");

    }

    public static void updateMenu(){
        bankThread.handler.post(new Runnable() {
            @Override
            public void run() {
                accountBalance.setText(bank.getFormattedLogAccountBalance());
                navName.setText(bank.getLogName());
                navEmail.setText(bank.getLogEmail());
            }
        });

    }
    public void onBackPressed(){

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }
}
