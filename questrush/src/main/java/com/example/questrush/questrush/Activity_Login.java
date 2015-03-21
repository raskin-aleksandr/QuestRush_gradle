package com.example.questrush.questrush;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.gc.materialdesign.views.ButtonRectangle;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class Activity_Login extends ActionBarActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        ButtonRectangle loginButton = (ButtonRectangle) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText name = (EditText) findViewById(R.id.loginName);
                EditText password = (EditText) findViewById(R.id.loginPassword);

                pd = new ProgressDialog(Activity_Login.this);
                pd.setTitle(getString(R.string.login_login));
                pd.setMessage(getString(R.string.loggin_in));
                pd.show();

                ParseUser.logInInBackground(name.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser arg0, ParseException e) {
                        if (e == null) {
                            pd.cancel();

                            Intent intent = new Intent(getApplicationContext(), Activity_QuestList.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        } else {
                            pd.cancel();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        final ButtonRectangle resetPassword = (ButtonRectangle) findViewById(R.id.resetButton);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog resetDialog = new Dialog(Activity_Login.this);
                resetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                resetDialog.setContentView(R.layout.dialog_reset);
                resetDialog.setTitle(getString(R.string.reset_title));

                final EditText eMail = (EditText) resetDialog.findViewById(R.id.reset);

                ButtonRectangle reset = (ButtonRectangle) resetDialog.findViewById(R.id.resetSend);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParseUser.requestPasswordResetInBackground(eMail.getText().toString(), new RequestPasswordResetCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.reset_toast) + eMail.getText().toString(), Toast.LENGTH_SHORT).show();
                                    resetDialog.cancel();
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    resetDialog.cancel();
                                }
                            }
                        });
                    }
                });
                resetDialog.show();
            }
        });
    }
}