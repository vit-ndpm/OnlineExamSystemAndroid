package com.degsnar.onlineexamsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences myPref=getSharedPreferences("userData",MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
            if (myPref.contains("token")){
                Intent intent=new Intent(SplashScreen.this,Home.class);
               startActivity(intent);
               finish();

            }else
            {
                Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            }
        }, 3000);
    }
}