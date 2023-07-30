package com.degsnar.onlineexamsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Paper extends AppCompatActivity {
    int paperId;
    String UserToken;
    int userId;
    int currentQuestionId;
    int currentPosition;
    int nextPosition;
    int nextQuestionId;
    RecyclerView recyPaper;
    TextView question_no, question,timer;
    Button submitResponse, nextQuestion,updateResponseBtn,clearResponseBtn;
    RadioGroup radioGroup;
    RadioButton opt1, opt2, opt3, opt4, selectedOption;
    ArrayList<Question> questionArrayList;
    QuestionRecyclerAdapter questionRecyclerAdapter;
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
        //start timer
        startCountDown();
        //initialize views
        initViews();
        recyPaper.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        questionArrayList = new ArrayList<>();
        fillQuestionsList();
        submitResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedOptionId = radioGroup.getCheckedRadioButtonId();


                if (selectedOptionId != -1) {
                    int selectedOptionNumber = -100;
                    if (selectedOptionId == opt1.getId()) {
                        selectedOptionNumber = 1;
                    } else if (selectedOptionId == opt2.getId()) {
                        selectedOptionNumber = 2;
                    } else if (selectedOptionId == opt3.getId()) {
                        selectedOptionNumber = 3;
                    } else if (selectedOptionId == opt4.getId()) {
                        selectedOptionNumber = 4;
                    }
                    selectedOption = findViewById(selectedOptionId);
                    saveReposne(selectedOptionNumber);
                } else {
                    Toast.makeText(Paper.this, "You have not selected Anything: ", Toast.LENGTH_SHORT).show();
                }


            }
        });
        updateResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedOptionId = radioGroup.getCheckedRadioButtonId();


                if (selectedOptionId != -1) {
                    int selectedOptionNumber = -100;
                    if (selectedOptionId == opt1.getId()) {
                        selectedOptionNumber = 1;
                    } else if (selectedOptionId == opt2.getId()) {
                        selectedOptionNumber = 2;
                    } else if (selectedOptionId == opt3.getId()) {
                        selectedOptionNumber = 3;
                    } else if (selectedOptionId == opt4.getId()) {
                        selectedOptionNumber = 4;
                    }
                    selectedOption = findViewById(selectedOptionId);
                    updateResponse(selectedOptionNumber);
                } else {
                    Toast.makeText(Paper.this, "You have not selected Anything: ", Toast.LENGTH_SHORT).show();
                }
            }
            });
        clearResponseBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int selectedOptionId = radioGroup.getCheckedRadioButtonId();


        if (selectedOptionId != -1) {
            int selectedOptionNumber = -100;
            if (selectedOptionId == opt1.getId()) {
                selectedOptionNumber = 1;
            } else if (selectedOptionId == opt2.getId()) {
                selectedOptionNumber = 2;
            } else if (selectedOptionId == opt3.getId()) {
                selectedOptionNumber = 3;
            } else if (selectedOptionId == opt4.getId()) {
                selectedOptionNumber = 4;
            }
            selectedOption = findViewById(selectedOptionId);
            clearResponse(selectedOptionNumber);
        } else {
            Toast.makeText(Paper.this, "You have not selected Anything: ", Toast.LENGTH_SHORT).show();
        }

    }
});
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextPosition<questionArrayList.size()){
                    setUpNextQuestion(nextPosition);
                }else {
                    new AlertDialog.Builder(Paper.this)
                            .setMessage("You have Reached to last Question?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Paper.super.onBackPressed();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

            }
        });

    }

    private void startCountDown() {
        CountDownTimer countDownTimer=new CountDownTimer(30*60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                int min= (int) (millisUntilFinished/(1000*60));
                int sec=(int)(millisUntilFinished-min*1000*60)/1000;
                timer.setText(min+":"+sec);

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    //save resposens to server
    private void saveReposne(int selectedOptionNumber) {
        submitResponse.setClickable(false);
        AndroidNetworking.initialize(this);
        String url = "https://exam.vinayakinfotech.co.in/api/setResponse";
        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .addHeaders("accept", "application/json")
                .addHeaders("Authorization", "Bearer " + UserToken)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("paper_id", String.valueOf(paperId))
                .addBodyParameter("question_id", String.valueOf(currentQuestionId))
                .addBodyParameter("selected_option", String.valueOf(selectedOptionNumber))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Paper.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        submitResponse.setClickable(true);

    }
    //update response
    private void updateResponse(int selectedOptionNumber) {
        AndroidNetworking.initialize(this);
        String url = "https://exam.vinayakinfotech.co.in/api/updateResponse";
        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .addHeaders("accept", "application/json")
                .addHeaders("Authorization", "Bearer " + UserToken)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("paper_id", String.valueOf(paperId))
                .addBodyParameter("question_id", String.valueOf(currentQuestionId))
                .addBodyParameter("selected_option", String.valueOf(selectedOptionNumber))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Paper.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    //Delete Response from server
    private void clearResponse(int selectedOptionNumber) {
        AndroidNetworking.initialize(this);
        String url = "https://exam.vinayakinfotech.co.in/api/delteResposne";
        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .addHeaders("accept", "application/json")
                .addHeaders("Authorization", "Bearer " + UserToken)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("paper_id", String.valueOf(paperId))
                .addBodyParameter("question_id", String.valueOf(currentQuestionId))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                radioGroup.clearCheck();
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Paper.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void fillQuestionsList() {
        SharedPreferences myPref = getSharedPreferences("userData", MODE_PRIVATE);
        if (myPref.contains("token")) {
            String token = myPref.getString("token", null);
            UserToken = token;
            userId = myPref.getInt("userId", -100);
            // Toast.makeText(this, "User ID: "+userId, Toast.LENGTH_SHORT).show();

            AndroidNetworking.initialize(Paper.this.getApplication());
            String url = "https://exam.vinayakinfotech.co.in/api/getAllQuestionByPaperId/" + paperId;
            AndroidNetworking.get(url).

                    addHeaders("accept", "application/json")
                    .addHeaders("Authorization", "Bearer " + token).
                    build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if (response.getString("status").equals("success")) {
                                    JSONArray jsonArray = response.getJSONArray("questions");
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            Question question = new Question();
                                            question.setId(jsonObject.getInt("id"));
                                            question.setPaper_id(jsonObject.getInt("paper_id"));
                                            question.setQuestion_no(jsonObject.getInt("question_no"));
                                            question.setCorrect_option(jsonObject.getInt("correct_option"));
                                            question.setSubject_id(jsonObject.getInt("subject_id"));
                                            question.setTopic_id(jsonObject.getInt("topic_id"));
                                            question.setQuestion(jsonObject.getString("question"));
                                            question.setOption1(jsonObject.getString("option1"));
                                            question.setOption2(jsonObject.getString("option2"));
                                            question.setOption3(jsonObject.getString("option3"));
                                            question.setOption4(jsonObject.getString("option4"));
                                            question.setDescription(jsonObject.getString("description"));
                                            questionArrayList.add(question);

                                        }
                                         questionRecyclerAdapter = new QuestionRecyclerAdapter(getApplicationContext(), questionArrayList) {

                                            @Override
                                            public void setUpQuestion(int position) {
                                                currentQuestionId = -100;
                                                radioGroup.clearCheck();
                                                currentQuestionId = questionArrayList.get(position).id;
                                                if ((position-2)<questionArrayList.size()){
                                                    nextQuestionId=questionArrayList.get(position+1).id;
                                                    nextPosition=position+1;
                                                }
                                                else {
                                                    return;
                                                }

                                                //Toast.makeText(Paper.this, String.valueOf(currentQuestionId), Toast.LENGTH_SHORT).show();
                                                question_no.setText(getString(R.string.q_no,String.valueOf(questionArrayList.get(position).question_no)));
                                                question.setText(questionArrayList.get(position).question);
                                                animateQuestion(question);
                                                opt1.setText(questionArrayList.get(position).option1);
                                                animateOption(opt1);
                                                opt2.setText(questionArrayList.get(position).option2);
                                                animateOption(opt2);
                                                opt3.setText(questionArrayList.get(position).option3);
                                                animateOption(opt3);
                                                opt4.setText(questionArrayList.get(position).option4);
                                                animateOption(opt4);
                                            }


                                        };
                                        recyPaper.setAdapter(questionRecyclerAdapter);

                                    }
                                   // Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                } else if (response.getString("status").equals("failed")) {
                                    Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                        }
                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(Paper.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
    private void animateQuestion(TextView question) {
        TranslateAnimation animObj = new TranslateAnimation(question.getWidth(), 0, 0, 0);
        animObj.setDuration(500);
        question.startAnimation(animObj);
    }
    private void animateOption(RadioButton option) {
        TranslateAnimation animObj = new TranslateAnimation(option.getWidth(), 0, 0, 0);
        animObj.setDuration(500);
        option.startAnimation(animObj);
    }
    private void initViews() {
        recyPaper = findViewById(R.id.recyPaper);
        timer=findViewById(R.id.timer);
        question_no = findViewById(R.id.q_no);
        question = findViewById(R.id.question);
        submitResponse = findViewById(R.id.submitResponse);
        nextQuestion = findViewById(R.id.nextQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        updateResponseBtn=findViewById(R.id.updateResponse);
        clearResponseBtn=findViewById(R.id.clearResponse);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Exam Alert")
                .setIcon(R.drawable.baseline_warning_24)
                .setMessage("Are you sure you want to close this Exam?, Only successfully submitted Response will be counted for Calculating your Exam Result")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void setUpNextQuestion(int position) {
        currentQuestionId = -100;
        radioGroup.clearCheck();
        currentQuestionId = questionArrayList.get(position).id;
        nextQuestionId=questionArrayList.get(position+1).id;
        nextPosition=position+1;
        //Toast.makeText(Paper.this, String.valueOf(currentQuestionId), Toast.LENGTH_SHORT).show();
        question_no.setText(getString(R.string.q_no,String.valueOf(questionArrayList.get(position).question_no)));
        question.setText(questionArrayList.get(position).question);
        animateQuestion(question);
        opt1.setText(questionArrayList.get(position).option1);
        animateOption(opt1);
        opt2.setText(questionArrayList.get(position).option2);
        animateOption(opt2);
        opt3.setText(questionArrayList.get(position).option3);
        animateOption(opt3);
        opt4.setText(questionArrayList.get(position).option4);
        animateOption(opt4);
    }
}