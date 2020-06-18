package com.example.android.spaghettiproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spaghettiproject.Retrofit.IMyService;
import com.example.android.spaghettiproject.Retrofit.RetrofitClient;

import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements ServerActivity.AsyncResponse {

    private EditText mEmail;
    private EditText mPassword1;
    private EditText mPassword2;
    private Button mSetupButton;
    private TextView mLogin;
    private String password;
    private EditText mName;
    private ProgressBar mProgressBar;

    IMyService iMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        mPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        mLogin = (TextView) findViewById(R.id.textViewLogin);
        mName = (EditText) findViewById(R.id.editTextName);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        String redPart = "* ";
        String greyPart = "Name";

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString redColoredString = new SpannableString(redPart);
        redColoredString.setSpan(new ForegroundColorSpan(Color.RED), 0, redPart.length(), 0);
        builder.append(redColoredString);

        SpannableString greyColoredString = new SpannableString(greyPart);
        greyColoredString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, greyPart.length(), 0);
        builder.append(greyColoredString);

        mName.setHint(builder);

        builder = new SpannableStringBuilder();
        greyPart = "Email";
        greyColoredString = new SpannableString(greyPart);
        greyColoredString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, greyPart.length(), 0);
        builder.append(redColoredString);
        builder.append(greyColoredString);

        mEmail.setHint(builder);

        builder = new SpannableStringBuilder();
        greyPart = "Password";
        greyColoredString = new SpannableString(greyPart);
        greyColoredString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, greyPart.length(), 0);
        builder.append(redColoredString);
        builder.append(greyColoredString);

        mPassword1.setHint(builder);

        builder = new SpannableStringBuilder();
        greyPart = "Confirm Password";
        greyColoredString = new SpannableString(greyPart);
        greyColoredString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, greyPart.length(), 0);
        builder.append(redColoredString);
        builder.append(greyColoredString);

        mPassword2.setHint(builder);


        Intent intent = getIntent();
        mEmail.setText(intent.getStringExtra("email"));

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("email", mEmail.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        //Registering once button pressed
        mSetupButton = (Button) findViewById(R.id.btnSetup);
        mSetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPassword1.getText().toString().equals(mPassword2.getText().toString())) {
                    password = mPassword1.getText().toString();

                    if (TextUtils.isEmpty(mEmail.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(RegisterActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(mName.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //Run below only if user account and password matches
                    new ServerActivity(RegisterActivity.this, mEmail.getText().toString(), mName.getText().toString(), mPassword1.getText().toString(), mProgressBar).execute(mName.getText().toString());

                }
                else
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("email", mEmail.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
    }

    @Override
    public void processFinish(String output) {
        switch (output) {
            case "success":
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Success!")
                        .setMessage("Please login with your new account")
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case "email exists":
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Registration Error")
                        .setMessage("Email already exists")
                        .setNegativeButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case "missing email":
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
                break;
            case "missing password":
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
                break;
            case "missing name":
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
