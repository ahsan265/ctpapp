package com.example.citytrafficpolice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private CheckBox checkbox;
    private Button login;
    private TextView forgotPassword;
    RequestQueue queue;
    android.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue = Volley.newRequestQueue(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        login = (Button) findViewById(R.id.login);
        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
        builder = new android.app.AlertDialog.Builder(this);

        if (!isConnected(LoginActivity.this)) buildDialog(LoginActivity.this).show();
        else {

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();

                if (email.length() == 0) {
                    email.setError("Enter your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    email.setError("Enter valid Email");
                } else if (password.length() == 0) {
                    password.setError("Enter your Password");
                } else if (password.length() < 6) {
                    password.setError("Password can not be less than 6 characters");
                } else {
                    Gson gson = new Gson();
                    AndroidNetworking.post("http://10.0.2.2:5000/api/v1/auth/Login")
                            .addBodyParameter("username", email.getText().toString())
                            .addBodyParameter("password", password.getText().toString())
                            //.addHeaders("Authorization","Bearer " + token)
                            .setTag("test")
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    String status = null;
                                    try
                                    {
                                        status = response.get("status").toString();
                                       // Log.i("status",status);
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    if (status != null && status.equals("200"))
                                    {
                                        JSONObject data;
                                        WardenAccount wardenAccount;
                                        try
                                        {
                                            data = response.getJSONObject("data");
                                            Log.i("data",data.toString());
                                            String jwtToken = response.get("jwtToken").toString();
                                            String id=data.get("id").toString();
                                            String fullname=data.get("fullName").toString();
                                            String email=data.get("email").toString();
                                            String phone=data.get("phone").toString();
                                            String CNIC=data.get("CNIC").toString();
                                            Log.i("id",id);
                                            wardenAccount = gson.fromJson(data.toString(), WardenAccount.class);
                                            //wardenAccount.setJwtToken(jwtToken);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("jwt",jwtToken);
                                            editor.putString("id",id);
                                            editor.putString("fullName",fullname);
                                            editor.putString("email",email);
                                            editor.putString("phone",phone);
                                            editor.putString("CNIC",CNIC);

                                            editor.apply();
                                            Log.i("status",jwtToken);
                                            MyAccountActivity.wardenAccount = wardenAccount;
                                            DataFormActivity.wardenAccount = wardenAccount;
                                            MainActivity.wardenAccount = wardenAccount;

                                            loadingDialog.startLoadingDialog();
                                            Handler handler = new Handler();

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(mainActivity);
                                                }
                                            },3000);

                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    } else
                                        {
                                        String message = null;

                                        try {
                                            message = response.get("message").toString();
                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onError(ANError error)
                                {
                                    JsonObject jsonObject = new JsonParser().parse(error.getErrorBody()).getAsJsonObject();
                                    String message = jsonObject.get("message").toString();
                                    message = message.substring(1, message.length() - 1);
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                if (value)
                {
                    // Show Password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                {
                    // Hide Password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // lambda used instead of clicllistener
        forgotPassword.setOnClickListener(v -> openForgotPassword());
    }

    // Internet Connection
    public boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    // Alert Dialog
    public AlertDialog.Builder buildDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection!");
        builder.setCancelable(false);
        builder.setMessage("You need to have Wifi or Mobile Data to proceed");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }

    public void openForgotPassword() {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

}