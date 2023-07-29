package com.degsnar.onlineexamsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button loginButton, registerButton, forgetPassword;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        AndroidNetworking.initialize(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt = email.getText().toString();
                String passwordtxt = password.getText().toString();
                boolean isValidCredentials = validateEmailAndPassword(emailtxt, passwordtxt);
                if (isValidCredentials) {
                    progressBar.setVisibility(View.VISIBLE);
                    // Toast.makeText(MainActivity.this, "email: "+emailtxt.toString()+" \n Password: "+passwordtxt, Toast.LENGTH_SHORT).show();
                    String url = "https://exam.vinayakinfotech.co.in/api/login";
                    AndroidNetworking.post(url).addBodyParameter("email", emailtxt)
                            .addBodyParameter("password", passwordtxt)
                            .addHeaders("accept", "application/json")
                            .build().
                            getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        if (response.getString("status").equals("success")){
                                            Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            SharedPreferences myPref=getSharedPreferences("userData",MODE_PRIVATE);
                                            SharedPreferences.Editor editor= myPref.edit();
                                            Toast.makeText(MainActivity.this, response.getString("token"), Toast.LENGTH_SHORT).show();

                                            editor.putString("token", (String) response.get("token"));
                                            editor.putInt("userId",  response.getJSONObject("user-data").getInt("id"));
                                            editor.putString("userName", (String) response.getJSONObject("user-data").getString("name"));
                                            editor.putString("userEmail", (String) response.getJSONObject("user-data").getString("email"));
                                            editor.putString("userMobile", (String) response.getJSONObject("user-data").getString("mobile"));
                                            editor.commit();
                                            Intent intent=new Intent(MainActivity.this,Home.class);
                                            startActivity(intent);
                                            //set progressbaar gone
                                            progressBar.setVisibility(View.GONE);
                                            //finish the activity so that user will not come to login screen again on back button press
                                         finish();
                                          // showSucessAlert( response.getString("message"));
                                        }
                                        else if (response.getString("status").equals("failed")) {
                                            Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);

                                    }

                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(MainActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                } else {
                    Toast.makeText(MainActivity.this, "email and Passwrod Validation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void showSucessAlert(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder((this));
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private boolean validateEmailAndPassword(String emailtxt, String passwordtxt) {
        return emailValidator(emailtxt) && isValidPassword(passwordtxt);

    }

    private void initViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.register);
        forgetPassword = findViewById(R.id.forgetPassword);
        progressBar=findViewById(R.id.myProgressBar);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}