package com.example.citytrafficpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity
{
    private EditText cnic;
    private EditText emailAddress;
    private Button proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        cnic = (EditText) findViewById(R.id.cnic);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        proceed = (Button) findViewById(R.id.proceed);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String cnicInput = cnic.getText().toString();
                String emailInput = emailAddress.getText().toString();
                if (cnicInput.isEmpty())
                {
                    cnic.setError("Enter Cnic");
                }
                else if (cnic.length() < 13)
                {
                    cnic.setError("Enter valid Cnic");
                }
                else if (emailInput.isEmpty())
                {
                    emailAddress.setError("Enter Email");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
                {
                    emailAddress.setError("Enter valid Email");
                }
                else
                    {
                        openUpdatePassword();
                    }
            }
    });


//        proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openUpdatePassword();
//            }
//        });
}

    private void openUpdatePassword() {
        Intent intent = new Intent(ForgotPassword.this, UpdatePassword.class);
        startActivity(intent);
    }
}
