package com.degsnar.onlineexamsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    boolean loginStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        String email = myPref.getString("userEmail", null);
        String password = myPref.getString("password", null);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (myPref.contains("token")) {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    boolean login(String email, String password) {
        String url = "https://exam.vinayakinfotech.co.in/api/login";
        AndroidNetworking.post(url).addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .addHeaders("accept", "application/json")
                .build().
                getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.getString("status").equals("success")) {
//                                SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
//                                SharedPreferences.Editor editor = myPref.edit();
//
//                                editor.putString("token", (String) response.get("token"));
//                                editor.putInt("userId", response.getJSONObject("user-data").getInt("id"));
//                                editor.putString("userName", (String) response.getJSONObject("user-data").getString("name"));
//                                editor.putString("userEmail", (String) response.getJSONObject("user-data").getString("email"));
//                                editor.putString("userMobile", (String) response.getJSONObject("user-data").getString("mobile"));
//                                editor.putString("password",password );
//                                editor.commit();
//
                                loginStatus = true;

                                //finish the activity so that user will not come to login screen again on back button press
                                finish();
                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(SplashScreen.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                loginStatus = false;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SplashScreen.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        loginStatus = false;
                    }
                });
        return loginStatus;
    }
}