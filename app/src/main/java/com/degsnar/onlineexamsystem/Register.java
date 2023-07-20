package com.degsnar.onlineexamsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText name,email,password,password_confirmation,phone;
    Button register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
//        progressBar.setVisibility(View.VISIBLE);
        Log.d("Mobile Num",String.valueOf(isValidMobileNo("6421212345")));

    }

    private void initViews() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        password_confirmation=findViewById(R.id.password_confirmatin);
        phone=findViewById(R.id.phone);
        progressBar=findViewById(R.id.myProgressBar);
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
    public static boolean  isValidMobileNo(String phone)
    {
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