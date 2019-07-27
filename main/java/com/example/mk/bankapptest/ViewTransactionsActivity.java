package com.example.mk.bankapptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import static com.example.mk.bankapptest.BankThread.bank;

public class ViewTransactionsActivity extends AppCompatActivity {
    ListView transactionsListView;
    static volatile String[] tranDates;
    static volatile String[] tranTypes;
    static volatile String[] tranDescs;
    static volatile String[] tranAmounts;
    TextView errorTextView;
    BankThread bankThread;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);
        bankThread = MainActivity.getBankThread();
        transactionsListView = (ListView) findViewById(R.id.transaction_list_view);
        errorTextView = (TextView) findViewById(R.id.view_transactions_error_text);
        getTransactionInfoTask Task = new getTransactionInfoTask();
        Task.execute();

    }

    public boolean transactionCheck(String[] types, String [] dates, String[] descs, String[] amounts){
        if(types != null || dates != null || descs != null || amounts != null){
            return true;
        }
        return false;
    }

    public void listMaker(){
        if(transactionCheck(tranTypes,tranDates,tranAmounts,tranDescs) == false){
            errorTextView.setText("No transaction present on your account.");
        }else {
            TransactionAdapter transactionAdapter = new TransactionAdapter(mContext, tranTypes, tranDates, tranAmounts, tranDescs);
            transactionsListView.setAdapter(transactionAdapter);
        }
    }

    private class  getTransactionInfoTask extends AsyncTask<String[],Integer,LinkedList>{
        String[] resultsTranType;
        String[] resultsTranDate;
        String[] resultsTranAmount;
        String[] resultTranDesc;
        LinkedList<String[]> accResults = new LinkedList();
        @Override
        protected LinkedList doInBackground(String[]... strings) {
            resultsTranType = bank.getAccTranTypes();
            resultsTranDate = bank.getAccTranDate();
            resultsTranAmount = bank.getAccTranAmount();
            resultTranDesc = bank.getAccTranDesc();
            accResults.add(resultsTranType);
            accResults.add(resultsTranDate);
            accResults.add(resultsTranAmount);
            accResults.add(resultTranDesc);
            return accResults;
        }

        @Override
        protected void onPostExecute(LinkedList strings) {
            super.onPostExecute(strings);
            tranTypes = accResults.get(0);
            tranAmounts = accResults.get(1);
            tranDates = accResults.get(2);
            tranDescs = accResults.get(3);
            listMaker();
        }
    }

}
