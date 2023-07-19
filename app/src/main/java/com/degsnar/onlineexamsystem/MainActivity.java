package com.degsnar.onlineexamsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button loginButton,registerButton,forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        AndroidNetworking.initialize(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt=email.getText().toString();
                String passwordtxt=password.getText().toString();
               boolean isValidCredentials= validateEmailAndPassword(emailtxt,passwordtxt);
               if (isValidCredentials){
                   Toast.makeText(MainActivity.this, "email: "+emailtxt.toString()+" \n Password: "+passwordtxt, Toast.LENGTH_SHORT).show();
                   String url="https://exam.vinayakinfotech.co.in/api/login";
                AndroidNetworking.post(url).addBodyParameter("email",emailtxt).addBodyParameter("password",passwordtxt).addHeaders("accept","application/json").build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Toast.makeText(MainActivity.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

               }
               else {
                   Toast.makeText(MainActivity.this, "email and Passwrod Validation failed", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    private boolean validateEmailAndPassword(String emailtxt, String passwordtxt) {
        if (emailValidator(emailtxt) && isValidPassword(passwordtxt))
        {
            return true;
        }
        else {
            return false;
        }

    }

    private void initViews() {
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginButton);
        registerButton=findViewById(R.id.register);
        forgetPassword=findViewById(R.id.forgetPassword);
    }
    public boolean emailValidator(String email)
    {
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