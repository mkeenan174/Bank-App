package com.example.mk.bankapptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

import static com.example.mk.bankapptest.BankThread.bank;


public class AccountSelectionActivity extends AppCompatActivity {
    ListView accountSelectionListView;
    static volatile   String [] accBalances;
    static volatile String [] accTypes;
    static volatile String [] accNumbers;
    BankThread bankThread;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_selection);
        bankThread = MainActivity.getBankThread();
        final TextView accSelectError = (TextView) findViewById(R.id.accSelectError);
        accountSelectionListView = (ListView) findViewById(R.id.AccountSelectionMenuView );

        bankThread.handler.post(new Runnable() {
            @Override
            public void run() {
                accTypes = bank.getUserAccTypes();
                accNumbers = bank.getUserAccNum();
                accBalances = bank.getUserAccBalance();
                Intent accArraysIntent = new Intent("getArrays");
                accArraysIntent.putExtra("theTypes",accTypes);
                accArraysIntent.putExtra("theNumbers",accNumbers);
                accArraysIntent.putExtra("theBalances",accBalances);

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(accArraysIntent);
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("getArrays"));
            /*
            accTypes = bank.getUserAccTypes();
            if(accTypes != null){
                accSelectError.setText("Error null accType array");
            }
            accBalances = bank.getUserAccBalance();
                if(accBalances != null){
                    accSelectError.setText("Error null accBalances array");
                }

            accNumbers = bank.getUserAccNum();
                if(accNumbers != null){
                    accSelectError.setText("Error null accNumbers array");
                }
            }*/

            String test = accTypes[0];
            accSelectError.setText(test);
            test = accNumbers[0];
            accSelectError.setText(test);
            test = accBalances[0];
            accSelectError.setText(test);




        if(accTypes != null || accBalances != null || accNumbers != null) {
            ItemAdapter itemAdapter = new ItemAdapter(this, accTypes, accBalances, accNumbers);
            accountSelectionListView.setAdapter(itemAdapter);
        }else{
            accSelectError.setText("Error null array");
        }

        accountSelectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent menuActivity = new Intent(getApplicationContext(), MainMenuActivity.class);
                //menuActivity.putExtra("com.example.mk.INDEX", position);
                bank.setLogAccount(position);
                startActivity(menuActivity);
            }
        });





    }

    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            accTypes = intent.getStringArrayExtra("theTypes");
            accBalances = intent.getStringArrayExtra("theBalances");
            accNumbers = intent.getStringArrayExtra("theNumbers");


        }
    };

}
