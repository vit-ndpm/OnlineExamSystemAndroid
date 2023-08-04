package com.degsnar.onlineexamsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Exam> examArrayList;
    RecyclerView examRecyclerView;

    boolean loginStatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        examRecyclerView=findViewById(R.id.examsRecyclerView);

        examRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setup toolbar here
        toolbar = findViewById(R.id.home_toolbar);
        examArrayList= new ArrayList<>();
        fillExamArrayList();

        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
    }

    private void fillExamArrayList() {
        ProgressDialog dialog = ProgressDialog.show(Home.this, "",
                "Loading. Please wait...", true);

        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        if (myPref.contains("token")) {
            String token = myPref.getString("token", null);


            AndroidNetworking.initialize(Home.this.getApplication());
            String url = "https://exam.vinayakinfotech.co.in/api/getAllPapers";
            AndroidNetworking.get(url).

                    addHeaders("accept", "application/json")
                    .addHeaders("Authorization", "Bearer " + token).
                    build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                JSONArray jsonArray=response.getJSONArray("papers");
                                Log.d("Response", jsonArray.toString());
                                examArrayList.clear();
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    Log.d("papers",jsonArray.getJSONObject(i).getString("paper_name"));
                                    String paper_name=jsonArray.getJSONObject(i).getString("paper_name");
                                    String available=jsonArray.getJSONObject(i).getString("available");
                                    String paper_type=jsonArray.getJSONObject(i).getString("paper_type");
                                    int id=jsonArray.getJSONObject(i).getInt("id");

                                   Exam exam=new Exam(id,paper_name,paper_type,available);

                                   examArrayList.add(exam);
                                    ExamRecyclerAdapter examRecyclerAdapter=new ExamRecyclerAdapter(getApplicationContext(),examArrayList);
                                    examRecyclerView.setAdapter(examRecyclerAdapter);

                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Home.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                            Log.e("Error", anError.getLocalizedMessage());
                        }
                    });
        }
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if (itemId==R.id.profile){
            showProfile();
        } else if (itemId==R.id.changePassword) {
                    changePassword();
        } else if (itemId==R.id.refresh) {
            fillExamArrayList();
        }else if (itemId==R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProfile() {
        Intent intent=new Intent(Home.this,UserProfile.class);
        startActivity(intent);
    }

    private void changePassword() {
        Intent intent=new Intent(Home.this,ChangePassword.class);
        startActivity(intent);
    }

    public void logout(){
        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        if (myPref.contains("token")) {
            String token = myPref.getString("token", null);


            AndroidNetworking.initialize(Home.this.getApplication());
            String url = "https://exam.vinayakinfotech.co.in/api/logout";
            AndroidNetworking.post(url).

                    addHeaders("accept", "application/json")
                    .addHeaders("Authorization", "Bearer " + token).
                    build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(Home.this, response.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Response", response.toString());
                            editor.remove("token");
                            editor.apply();
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Home.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                            Log.e("Error", anError.getLocalizedMessage());
                        }
                    });
        }

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
                                Toast.makeText(Home.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                loginStatus = false;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Home.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        loginStatus = false;
                    }
                });
        return loginStatus;
    }
}