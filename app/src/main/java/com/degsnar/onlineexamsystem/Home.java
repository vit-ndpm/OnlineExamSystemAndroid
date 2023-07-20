package com.degsnar.onlineexamsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class Home extends AppCompatActivity {
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPref.edit();
                if (myPref.contains("token")) {
                    String token = myPref.getString("token", null);



                    AndroidNetworking.initialize(Home.this.getApplication());
                    String url = "https://exam.vinayakinfotech.co.in/api/logout";
                    AndroidNetworking.post(url).

                            addHeaders("accept", "application/json")
                            .addHeaders("Authorization","Bearer " +token).
                            build().getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(Home.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response.toString());
                                    editor.remove("token");
                                    editor.commit();
                                    Intent intent=new Intent(Home.this, MainActivity.class);
                                    startActivity(intent);


                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(Home.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                                    Log.e("Error", anError.getLocalizedMessage());
                                }
                            });
                }
            }
        });


    }
}