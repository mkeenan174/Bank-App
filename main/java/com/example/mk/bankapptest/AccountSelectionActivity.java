package com.example.mk.bankapptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import static com.example.mk.bankapptest.BankThread.bank;


public class AccountSelectionActivity extends AppCompatActivity {
    ListView accountSelectionListView;
    static volatile String[][] resultsArray;
    static volatile String [] accBalances;
    static volatile String [] accTypes;
    static volatile String [] accNumbers;
    static volatile String [] tranTypes;
    static volatile String [] tranDates;
    static volatile String [] tranDescs;
    static volatile String [] tranAmounts;
    static BankThread bankThread;
    static TextView accSelectError;
    Intent menuActivity;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_selection);
        bankThread = MainActivity.getBankThread();
        accSelectError = (TextView) findViewById(R.id.accSelectError);
        accountSelectionListView = (ListView) findViewById(R.id.AccountSelectionMenuView );
        menuActivity = new Intent(getApplicationContext(), MainMenuActivity.class);


        getAccountInfoTask task = new getAccountInfoTask();
        task.execute(tranTypes);
        if(task.getStatus() == AsyncTask.Status.FINISHED){
            accSelectError.setText(tranTypes[0]);
        }

    }




private class  getAccountInfoTask extends AsyncTask<String[],Integer,LinkedList>{
    String[] resultsAccType;
    String[] resultsAccNum;
    String[] resultsAccBal;
    LinkedList<String[]> accResults = new LinkedList();
    @Override
    protected LinkedList doInBackground(String[]... strings) {
        resultsAccType = bank.getUserAccTypes();
        resultsAccNum = bank.getUserAccNum();
        resultsAccBal = bank.getUserAccBalance();
        accResults.add(resultsAccType);
        accResults.add(resultsAccNum);
        accResults.add(resultsAccBal);
        return accResults;
    }

    @Override
    protected void onPostExecute(LinkedList strings) {
        super.onPostExecute(strings);
        accTypes = accResults.get(0);
        accNumbers = accResults.get(1);
        accBalances = accResults.get(2);
        ItemAdapter itemAdapter = new ItemAdapter(mContext, accTypes, accBalances, accNumbers);
        accountSelectionListView.setAdapter(itemAdapter);
        Toast.makeText(mContext, accTypes[0], Toast.LENGTH_SHORT).show();
        accountSelectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bank.setLogAccount(position);
                startActivity(menuActivity);
            }
        });
    }
}

    public void onBackPressed(){

    }
}
