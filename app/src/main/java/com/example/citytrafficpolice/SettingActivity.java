package com.example.citytrafficpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView faq = findViewById(R.id.faq);
        TextView contactUs = findViewById(R.id.contactUs);
        TextView privacyPolicy = findViewById(R.id.privacyPolicy);

        TextView appInfo = findViewById(R.id.appInfo);
        appInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAppInfo();
            }
        });
    }

    private void openAppInfo() {
        Intent intent = new Intent(this, AppInfo.class);
        startActivity(intent);
    }
}
