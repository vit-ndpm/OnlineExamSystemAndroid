package com.degsnar.onlineexamsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText nameEt, emailEt, passwordEt, password_confirmationEt, phoneEt;
    Button register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
//        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.initialize(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, password, password_confirmation, phone;
                name = nameEt.getText().toString();
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();
                password_confirmation = password_confirmationEt.getText().toString();
                phone = phoneEt.getText().toString();
                Toast.makeText(Register.this, phone.toString(), Toast.LENGTH_SHORT).show();
                if (isValidInputs(name, email, password, password_confirmation, phone)) {
                   // Toast.makeText(Register.this, "Client Side Input Validation Passed proceeding Further", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                    String url="https://exam.vinayakinfotech.co.in/api/registerUser";
                    AndroidNetworking.post(url)
                            .setPriority(Priority.HIGH)
                            .addHeaders("accept", "application/json")
                            .addBodyParameter("name",name)
                            .addBodyParameter("email",email)
                            .addBodyParameter("password",password)
                            .addBodyParameter("password_confirmation",password_confirmation)
                            .addBodyParameter("mobile",phone)
                            .build().getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        if (response.getString("status").equals("success")){
                                            Toast.makeText(Register.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            SharedPreferences myPref=getSharedPreferences("userData",MODE_PRIVATE);
                                            SharedPreferences.Editor editor= myPref.edit();
                                            Toast.makeText(Register.this, response.getString("token"), Toast.LENGTH_SHORT).show();

                                            editor.putString("token", (String) response.get("token"));
                                            editor.commit();
                                            Intent intent=new Intent(Register.this,Home.class);
                                            startActivity(intent);
                                            //set progressbaar gone
                                            progressBar.setVisibility(View.GONE);
                                            //finish the activity so that user will not come to login screen again on back button press
                                             finish();
                                            // showSucessAlert( response.getString("message"));
                                        }
                                        else if (response.getString("status").equals("failed")) {
                                            Toast.makeText(Register.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
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
                    Toast.makeText(Register.this, "Validation Failed Please Check again and Enter Valid Inputs", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isValidInputs(String name, String email, String password, String password_confirmation, String phone) {
        boolean isValidName = !name.isEmpty() && name.length() > 3;
        Log.d("isValid Name",String.valueOf(isValidName));
        boolean isValidEmail = isValidEMail(email);
        Log.d("isValid Email",String.valueOf(isValidEmail));
        boolean isValidPass = isValidPassword(password) && isValidPassword(password_confirmation) && password.equals(password_confirmation);
        Log.d("isValid Password",String.valueOf(isValidPass));
        boolean isValidMobile = isValidMobileNo(phone);
        Log.d("isValid Phone",String.valueOf(isValidMobile));
        if (isValidName&&isValidEmail&&isValidPass&&isValidMobile){
            return true;
        }else {
            return false;
        }
    }

    private void initViews() {
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        password_confirmationEt = findViewById(R.id.password_confirmatin);
        phoneEt = findViewById(R.id.phone);
        progressBar = findViewById(R.id.myProgressBar);
        register=findViewById(R.id.register);
    }

    public boolean isValidEMail(String email) {
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

    public static boolean isValidMobileNo(String phone) {
        //(0/91): number starts with (0/91)
        //[7-9]: starting of the number may contain a digit between 6 to 9
        //[0-9]: then contains digits 0 to 9
        Pattern ptrn = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        //the matcher() method creates a matcher that will match the given input against this pattern
        Matcher match = ptrn.matcher(phone);
        //returns a boolean value
        return (match.find() && match.group().equals(phone));
    }
}