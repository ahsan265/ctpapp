package com.example.citytrafficpolice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MyAccountActivity extends AppCompatActivity {

    public static int RESULT_LOAD_IMAGE = 1;
    private TextView name;
    private TextView wardenId;
    private TextView email;
    private TextView cnic;
    public static WardenAccount wardenAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        email = (TextView) findViewById(R.id.email);
        cnic = (TextView) findViewById(R.id.cnic);
        name = (TextView) findViewById(R.id.name);
        wardenId = (TextView) findViewById(R.id.wardenId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView setImage = (TextView) findViewById(R.id.setImage);
        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        if (wardenAccount != null)
        {
            name.setText(wardenAccount.getFullName());
            wardenId.setText(wardenAccount.getId());
            email.setText(wardenAccount.getEmail());
            cnic.setText(wardenAccount.getCNIC());
        }
 }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.image);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}