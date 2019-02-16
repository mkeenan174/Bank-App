package com.example.mk.bankapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflator;
    String [] accBalances;
    String [] accTypes;
    String [] accNumbers;

    public ItemAdapter(Context c, String[] types, String[] balances, String[] numbers){
        accTypes = types;
        accBalances = balances;
        accNumbers = numbers;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return accTypes.length ;
    }

    @Override
    public Object getItem(int position) {
        return accTypes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.account_selection_detail,null);
        TextView accTypeTextView = (TextView) v.findViewById(R.id.accTypeTextView);
        TextView accnumTextView = (TextView) v.findViewById( R.id.accNumTextView);
        TextView accBalTextView = (TextView) v.findViewById( R.id.accBalTextView);

        String accType = accTypes[position];
        String accBal = accBalances[position];
        String accNum = accNumbers[position];

        accTypeTextView.setText(accType);
        accBalTextView.setText(accBal);
        accnumTextView.setText(accNum);

        return v;
    }
}
