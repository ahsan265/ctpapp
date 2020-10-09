package com.example.citytrafficpolice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity
{
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mInHome = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mInHome);
                SplashActivity.this.finish();
            }
        },3000);
    }
}
