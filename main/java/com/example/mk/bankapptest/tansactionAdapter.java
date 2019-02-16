package com.example.mk.bankapptest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class tansactionAdapter extends BaseAdapter {
    LayoutInflater mInflator;
    String [] tranDesciptions;
    String [] tranTypes;
    String [] tranAmounts;
    String [] tranDates;

    @Override
    public int getCount() {
       return  tranTypes.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
