package com.example.mk.bankapptest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.mk.bankapptest.BankThread.bank;

public class LogoutDialogA extends AppCompatDialogFragment {
    BankThread bankThread;
    Intent i;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        bankThread = MainActivity.getBankThread();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_logout_dialog, null);
        Button yesButton = (Button)  view.findViewById(R.id.yes_btn);
        Button noButton = (Button)  view.findViewById(R.id.no_btn);


        builder.setView(view);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MainActivity.class);
                Toast.makeText(getActivity(),"Logged out",Toast.LENGTH_SHORT).show();
                bankThread.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bank.logOut();
                    }
                });
                startActivity(i);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        return builder.create();
    }
}
