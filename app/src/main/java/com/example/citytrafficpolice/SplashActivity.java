package com.example.citytrafficpolice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
            { SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String name = preferences.getString("jwt", "");
                Log.i("jwt",name);
                if(!name.equalsIgnoreCase(""))
                {
                    Intent mInHome = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mInHome);
                    SplashActivity.this.finish();
                }
                else
                {
                    Intent mInHome = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mInHome);
                    SplashActivity.this.finish();
                }

            }
        },3000);
    }
}
