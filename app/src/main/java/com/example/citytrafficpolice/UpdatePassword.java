package com.example.citytrafficpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePassword extends AppCompatActivity
{
    private EditText newPassword;
    private EditText confirmPassword;
    private Button updatePassword;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        checkbox =  findViewById(R.id.checkbox);
        updatePassword = findViewById(R.id.updatePassword);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPassword.length()== 0)
                {
                    newPassword.setError("Enter your new Password");
                }
                else if (newPassword.length() < 6 )
                {
                    newPassword.setError("Password can not be less than 6 characters");
                }
                else if(confirmPassword.length()== 0)
                {
                    confirmPassword.setError("Confirm your new Password");
                }
                else if (confirmPassword.length() < 6 )
                {
                    confirmPassword.setError("Password can not be less than 6 characters");
                }
                else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    Toast.makeText(UpdatePassword.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }
                else { openLogin(); }
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                if (value)
                {
                    // Show Password
                    newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                {
                    // Hide Password
                    newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void openLogin() {
        Intent intent = new Intent(UpdatePassword.this, LoginActivity.class);
        startActivity(intent);
    }
}
