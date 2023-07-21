package com.degsnar.onlineexamsystem;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {
    EditText registeredEmail;
    TextView status;
    Button submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        registeredEmail=findViewById(R.id.register_email);
        status=findViewById(R.id.status);
        submit=findViewById(R.id.submit);
        progressBar=findViewById(R.id.forgetProgressBar);
        AndroidNetworking.initialize(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= String.valueOf(registeredEmail.getText());
                if (emailValidator(email))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String url="https://exam.vinayakinfotech.co.in/api/forgetPassword";
                    AndroidNetworking.post(url).setPriority(Priority.HIGH)
                            .addBodyParameter("email",email).build().
                            getAsJSONObject(new JSONObjectRequestListener()
                            {
                             @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("status").equals("success"))
                                {
                                    Toast.makeText(ForgetPassword.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    showSucessAlert( response.getString("message"));
                                }
                                else if (response.getString("status").equals("failed"))
                                {
                                    Toast.makeText(ForgetPassword.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    status.setVisibility(View.VISIBLE);
                                    status.setText(response.getString("message"));
                                    status.setTextColor(Color.parseColor("#FF0000"));
                                    progressBar.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            status.setVisibility(View.VISIBLE);
                            status.setText(anError.getErrorBody());
                            status.setTextColor(Color.parseColor("#FF0000"));
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Please Enter Valid Email Address");
                    status.setTextColor(Color.parseColor("#FF0000"));
                }

            }
        });


    }
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void showSucessAlert(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder((this));
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}