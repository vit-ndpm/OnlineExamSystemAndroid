package com.degsnar.onlineexamsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {
    EditText newPassword,confirmPassword;
    Button changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newPassword=findViewById(R.id.newPasswordET);
        confirmPassword=findViewById(R.id.confirm_passwordET);
        changePasswordBtn=findViewById(R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=newPassword.getText().toString();
                String password_confirmation=confirmPassword.getText().toString();
                if (password.equals(password_confirmation)){
                    if (isValidPassword(password)){


                        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);

                        String UserToken=myPref.getString("token",null);
                        Toast.makeText(ChangePassword.this, UserToken, Toast.LENGTH_SHORT).show();

                        AndroidNetworking.initialize(ChangePassword.this);
                        String url = "https://exam.vinayakinfotech.co.in/api/changePassword";
                        AndroidNetworking.get(url)
                                .setPriority(Priority.HIGH)
                                .addHeaders("accept", "application/json")
                                .addHeaders("Authorization", "Bearer " + UserToken)
                                .addQueryParameter("password", password)
                                .addQueryParameter("password_confirmation", password_confirmation).build().getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getString("status").equals("success")){

                                               SharedPreferences.Editor editor=myPref.edit();
                                               editor.remove("token");
                                                Toast.makeText(ChangePassword.this,response.getString("message") , Toast.LENGTH_SHORT).show();
                                               Intent intent=new Intent(ChangePassword.this,MainActivity.class);
                                               startActivity(intent);

                                            } else if (response.getString("status").equals("failed")) {

                                                Toast.makeText(ChangePassword.this,response.getString("message") , Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                    }
                                });

                    }else {
                        Toast.makeText(ChangePassword.this, "Invalid Password ,Please Enter Valid Password, Try Again", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(ChangePassword.this, "Password and Confirm Password MisMatch, Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void showSucessAlert(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder((this));
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
//                        Intent myIntent=new Intent(ChangePassword.this,Home.class);
//                        startActivity(myIntent);
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}