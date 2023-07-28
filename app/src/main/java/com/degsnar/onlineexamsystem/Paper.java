package com.degsnar.onlineexamsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Paper extends AppCompatActivity {
    int paperId;
    RecyclerView recyPaper;
    TextView question_no, question;
    Button submitResponse, nextQuestion;
    RadioGroup radioGroup;
    RadioButton opt1, opt2, opt3, opt4;
    ArrayList<Question> questionArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        //Get paperId from Bundle
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            paperId = b.getInt("paperId");

        }
        //initialize views
        initViews();
        recyPaper.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        questionArrayList = new ArrayList<Question>();
        fillQuestionsList();
        questionArrayList.add(new Question(1, 1));
        questionArrayList.add(new Question(2, 2));
        questionArrayList.add(new Question(3, 3));
        questionArrayList.add(new Question(4, 4));
        questionArrayList.add(new Question(5, 5));
        questionArrayList.add(new Question(6, 6));
        questionArrayList.add(new Question(7, 7));
        questionArrayList.add(new Question(8, 8));
        questionArrayList.add(new Question(9, 9));
        questionArrayList.add(new Question(10, 10));
        questionArrayList.add(new Question(11, 11));
        questionArrayList.add(new Question(12, 12));
        questionArrayList.add(new Question(13, 13));
        questionArrayList.add(new Question(14, 14));
        questionArrayList.add(new Question(15, 15));
        questionArrayList.add(new Question(16, 16));
        questionArrayList.add(new Question(17, 17));
        questionArrayList.add(new Question(18, 18));
        questionArrayList.add(new Question(19, 19));
        questionArrayList.add(new Question(20, 20));
        questionArrayList.add(new Question(21, 21));
        questionArrayList.add(new Question(22, 22));
        questionArrayList.add(new Question(23, 23));
        questionArrayList.add(new Question(24, 24));

        QuestionRecyclerAdapter questionRecyclerAdapter = new QuestionRecyclerAdapter(this, questionArrayList);
        recyPaper.setAdapter(questionRecyclerAdapter);


    }

    private void fillQuestionsList() {
        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        if (myPref.contains("token")) {
            String token = myPref.getString("token", null);


            AndroidNetworking.initialize(Paper.this.getApplication());
            String url = "https://exam.vinayakinfotech.co.in/api/getAllQuestionByPaperId/" + paperId;
            AndroidNetworking.get(url).

                    addHeaders("accept", "application/json")
                    .addHeaders("Authorization", "Bearer " + token).
                    build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("success").equals("success")) {

                                }


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }


                        @Override
                        public void onError(ANError anError) {

                        }
                    });
                    }




        }

        private void initViews () {
            recyPaper = findViewById(R.id.recyPaper);
            question_no = findViewById(R.id.q_no);
            question = findViewById(R.id.question);
            submitResponse = findViewById(R.id.submitResponse);
            nextQuestion = findViewById(R.id.nextQuestion);
            radioGroup = findViewById(R.id.radioGroup);
            opt1 = findViewById(R.id.opt1);
            opt2 = findViewById(R.id.opt2);
            opt3 = findViewById(R.id.opt3);
            opt4 = findViewById(R.id.opt4);
        }
    }