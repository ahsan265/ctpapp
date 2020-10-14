package com.example.citytrafficpolice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataFormActivity extends AppCompatActivity {
    private EditText wardenName;
    private EditText wardenID;
    private EditText congestiondetails;

    Spinner timeDuration;
    Spinner roadBlock;
    Spinner trafficIncident;
    private TextView timeDate;
    private Button Submit;
    AlertDialog.Builder builder;
    public static WardenAccount wardenAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataform);

//        timeDate = findViewById(R.id.timeDate);
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy          h:mm a");
//        String dateTime = simpleDateFormat.format(calendar.getTime());
//        timeDate.setText(dateTime);

        // Submit Button
        Submit = findViewById(R.id.Submit);
        congestiondetails=findViewById(R.id.congestiondetails);
        wardenName = findViewById(R.id.wardenName);
        wardenID = findViewById(R.id.wardenID);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String jwt = preferences.getString("jwt", "");
        String fullName = preferences.getString("fullName", "");
        String id = preferences.getString("id", "");

        if (!fullName.equalsIgnoreCase("")) {
            wardenName.setText(fullName);
            wardenID.setText(id);
        }

        // Internet Connection
        if (!isConnected(DataFormActivity.this)) buildDialog(DataFormActivity.this).show();
        else { }

        //Time Duration
        timeDuration = findViewById(R.id.timeDuration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.Time_Duration, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        timeDuration.setAdapter(adapter);

        // Road Block
        roadBlock = findViewById(R.id.roadBlock);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (this, R.array.Road_Block, R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        roadBlock.setAdapter(adapter1);

        //Traffic Incident
        trafficIncident = findViewById(R.id.trafficIncident);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource
                (this, R.array.Traffic_Incident, R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        trafficIncident.setAdapter(adapter2);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String congdetl= "Not Defined";
                Intent intent = getIntent();
                String origin = intent.getStringExtra("origin");
                String destination = intent.getStringExtra("destination");
                String trafficType = trafficIncident.getSelectedItem().toString();
//                String trafficType = "road accident";
               // String congestionDetails = trafficIncident.getSelectedItem().toString();
                String congestionTime = timeDuration.getSelectedItem().toString();

                 congdetl=congestiondetails.getText().toString();
                Log.i("response", trafficType);

                Log.i("response", jwt);
                Gson gson = new Gson();
                AndroidNetworking.post("http://10.0.2.2:5000/api/v1/report/report-traffic")
                        .addBodyParameter("congestionTime", congestionTime)
                        .addBodyParameter("trafficType", trafficType)
                        .addBodyParameter("origin", origin)
                        .addBodyParameter("destination", destination)
                        .addBodyParameter("congestionDetails", congdetl)
                        .addHeaders("Authorization",jwt)
                        .setTag("test")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(JSONObject response) {
                                String status = null;
                                try
                                {
                                    status = response.get("status").toString();
                                    Log.i("status",status);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                final AlertDialog.Builder builder = new AlertDialog.Builder(DataFormActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dataformdialog, viewGroup, false);
                                Button buttonOk=dialogView.findViewById(R.id.buttonOk);
                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openMainActivity();
                                    }
                                    private void openMainActivity() {
                                        Intent mainActivity = new Intent(DataFormActivity.this, MainActivity.class);
                                        startActivity(mainActivity);
                                    }
                                });
                                alertDialog.show();
                            }

                            @Override
                            public void onError(ANError error) {
                                Log.i("error",error.toString());
                                Toast.makeText(DataFormActivity.this, "Failed to send the Data!", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


//        Submit.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                openMainActivity();
//            }
//        });
   }

    private void openMainActivity()
    {
        Intent mainActivity = new Intent(DataFormActivity.this, MainActivity.class);
        startActivity(mainActivity);
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
    public androidx.appcompat.app.AlertDialog.Builder buildDialog(Context context)
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection!");
        builder.setCancelable(false);
        builder.setMessage("You need to have Wifi or Mobile Data to proceed");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openMainActivity();
            }

            private void openMainActivity() {
                Intent mainActivity = new Intent(DataFormActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
        return builder;
    }
}