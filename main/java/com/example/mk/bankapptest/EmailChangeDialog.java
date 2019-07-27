package com.example.mk.bankapptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.mk.bankapptest.BankThread.bank;

public class EmailChangeDialog extends AppCompatDialogFragment  {
    BankThread bankThread;
    Intent i;
    EditText passwordInput;
    EditText newEmailInput;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        bankThread = MainActivity.getBankThread();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_email_dialogue, null);
        Button confirmButton = (Button) view.findViewById(R.id.confirm_change_btn);
        passwordInput = (EditText) view.findViewById(R.id.password_input);
        newEmailInput = (EditText) view.findViewById(R.id.new_email_input);
        builder.setView(view);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView errorText = (TextView) view.findViewById(R.id.change_email_error_text);
                final String newEmail = newEmailInput.getText().toString();
                final String p = passwordInput.getText().toString();
               bankThread.handler.post(new Runnable() {
                   @Override
                   public void run() {
                   if(bank.passwordCheck(p) == true && newEmail != null){
                       bank.getLogUser().changeAddress("Test");
                       Toast.makeText(getActivity(),"Email Changed to "+  bank.getLogUser().getAddress() ,Toast.LENGTH_SHORT).show();
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               MainMenuActivity.updateMenu();
                           }
                       });
                       dismiss();
                   }else{
                      errorText.setText("Incorrect Password");
                      errorText.setTextColor(Color.RED);
                   }
                   }
               });
            }
        });




      return builder.create();
    }



}
