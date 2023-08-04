package com.degsnar.onlineexamsystem;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
private  static  final int CAMERA_REQUEST=1888;
    TextView userName, userEmail, userMobile, userToken;
    ImageView profilePic, captureImage;
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.userNameTv);
        userEmail = findViewById(R.id.userEmailTv);
        userMobile = findViewById(R.id.userMobileTv);
        userToken = findViewById(R.id.tokenTv);
        profilePic = findViewById(R.id.profileImage);
        captureImage = findViewById(R.id.captureImage);
        homeBtn = findViewById(R.id.homeBtn);
        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        String name = myPref.getString("userName", null);
        String email = myPref.getString("userEmail", null);
        String mobile = myPref.getString("userMobile", null);
        String token = myPref.getString("token", null);
        userName.setText(name);
        userEmail.setText(email);
        userMobile.setText(mobile);
        userToken.setText(token);
        homeBtn.setOnClickListener(v -> {
            Intent myIntent = new Intent(UserProfile.this, Home.class);
            startActivity(myIntent);


        });


        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }


        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);
        }
    }
}


