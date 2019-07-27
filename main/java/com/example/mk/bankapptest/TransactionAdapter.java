package com.example.mk.bankapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class TransactionAdapter extends BaseAdapter {
    LayoutInflater mInflator;
    String [] tranDesciptions;
    String [] tranTypes;
    String [] tranAmounts;
    String [] tranDates;


    public TransactionAdapter(Context c, String[] types, String[] dates, String[] amounts, String[] descs){
        this.tranTypes = types;
        this.tranAmounts = amounts;
        this.tranDates = dates;
        this.tranDesciptions = descs;
        this.mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
       return  tranTypes.length;
    }

    @Override
    public Object getItem(int position) {
        return tranTypes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.transactionview_detail, null);
        TextView tranDescTV = (TextView) v.findViewById(R.id.tran_desc_text_view);
        TextView tranTypeTV = (TextView) v.findViewById(R.id.tran_type_text_view);
        TextView tranAmountTV = (TextView) v.findViewById(R.id.tran_amount_text_view);
        TextView tranDateTV = (TextView) v.findViewById(R.id.tran_date_text_view);

        String tranDesc = tranDesciptions[position];
        String tranAmount = tranAmounts[position];
        String tranDate = tranDates[position];
        String tranType = tranTypes[position];


        tranTypeTV.setText(tranType);
        tranDateTV.setText(tranDate);
        tranAmountTV.setText(tranAmount);
        tranDescTV.setText(tranDesc);
        return v;
    }
}
