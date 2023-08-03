package com.degsnar.onlineexamsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class Result extends AppCompatActivity {
    int paperId, userId;
    int correctAnswers;
    String UserToken;
    ArrayList<Question> questionArrayList;
    ArrayList<UserResponse> responseArrayList;
    ArrayList<ResultModel>resultArrayList;
    Button seeResult,gotoHome;
    RecyclerView resultRecyclerView;
    boolean isQuestionLoaded;
    boolean isResponseLoaded;
    TextView totalQuestions,totalAttempted,totalCorrect,percentage;
    ImageView smilyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        seeResult=findViewById(R.id.seeResult);
        resultRecyclerView=findViewById(R.id.resultRecyclerView);
        totalAttempted=findViewById(R.id.attempted);
        totalQuestions=findViewById(R.id.totalQuestion);
        totalCorrect=findViewById(R.id.correctQuestion);
        percentage=findViewById(R.id.percentage);
        smilyImage=findViewById(R.id.imgSmily);
        gotoHome=findViewById(R.id.gotoHome);
        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Result.this, Home.class);
               Result.this.startActivity(intent);

            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            paperId = b.getInt("paperId");
            userId = b.getInt("userId");
            UserToken = b.getString("UserToken");
            // Toast.makeText(this, "Details PaperID: "+paperId+" UserID: "+userId+"Token :"+UserToken, Toast.LENGTH_SHORT).show();
        }
        responseArrayList = new ArrayList<>();
        questionArrayList = new ArrayList<>();
        resultArrayList=new ArrayList<>();

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(Result.this));
        resultRecyclerView.setHasFixedSize(true);

        isQuestionLoaded=false;
        isResponseLoaded=false;
        getAllQuestions();
        getAllResponse();




        seeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
                if (isQuestionLoaded&&isResponseLoaded){
                    ResultRecyclerAdapter resultRecyclerAdapter=new ResultRecyclerAdapter(Result.this,questionArrayList,responseArrayList);
                    resultRecyclerView.setAdapter(resultRecyclerAdapter);
                }

            }
        });

    }



    private void getAllQuestions() {

        AndroidNetworking.initialize(Result.this);
        String url = "https://exam.vinayakinfotech.co.in/api/getAllQuestionByPaperId/" + paperId;
        AndroidNetworking.get(url).

                addHeaders("accept", "application/json")
                .addHeaders("Authorization", "Bearer " + UserToken).
                build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProgressDialog dialog = ProgressDialog.show(Result.this, "",
                                "Loading. Please wait...", true);
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
                                       // Log.d("total Questions:",String.valueOf(questionArrayList.size()));

                                    }
                                    isQuestionLoaded=true;
                                    totalQuestions.setText(String.valueOf(questionArrayList.size()));
                                }
                                // Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Result.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Result.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getAllResponse() {
        ProgressDialog dialog = ProgressDialog.show(Result.this, "",
                "Loading. Please wait...", true);
        AndroidNetworking.initialize(this);
        String url = "https://exam.vinayakinfotech.co.in/api/getAllResponse";
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("accept", "application/json")
                .addHeaders("Authorization", "Bearer " + UserToken)
                .addQueryParameter("user_id", String.valueOf(userId))
                .addQueryParameter("paper_id", String.valueOf(paperId))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d("Response", response.toString());
                            if (response.getString("status").equals("success")) {
                                Toast.makeText(Result.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.d("Response", response.toString());

                                JSONArray allResponses = response.getJSONArray("responses");
                                for (int i = 0; i < allResponses.length(); i++) {
                                    UserResponse userResponse = new UserResponse();
                                    JSONObject singleResponse = allResponses.getJSONObject(i);
                                    userResponse.setId(singleResponse.getInt("id"));
                                    userResponse.setUser_id(singleResponse.getInt("user_id"));
                                    userResponse.setQuestion_id(singleResponse.getInt("question_id"));
                                    userResponse.setPaper_id(singleResponse.getInt("paper_id"));
                                    userResponse.setSelected_option(singleResponse.getInt("selected_option"));
                                    responseArrayList.add(userResponse);
                                    Log.d("Total Response Found:", String.valueOf(responseArrayList.size()));
                                }
                                isResponseLoaded=true;
                                totalAttempted.setText(String.valueOf(responseArrayList.size()));
                                calculateResult();


                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Result.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", anError.getLocalizedMessage());
                        Toast.makeText(Result.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void calculateResult() {
         correctAnswers=0;
        for (int i = 0; i < questionArrayList.size(); i++) {
            int currentQuestionId=questionArrayList.get(i).getId();
            for (int j = 0; j <responseArrayList.size() ; j++) {
                int questionIdFromResponseList=responseArrayList.get(j).question_id;
                if (currentQuestionId==questionIdFromResponseList){
                    if (questionArrayList.get(i).correct_option==responseArrayList.get(j).selected_option){
                        correctAnswers++;
                    }

                }
            }


        }
        totalCorrect.setText(String.valueOf(correctAnswers));
        if (questionArrayList.size()>0){
           int totalQuestions=questionArrayList.size();
           float deciValue=correctAnswers*100.0f/totalQuestions;
            Log.d("%",String.valueOf(deciValue));
            percentage.setText(String.format("%.2f", deciValue)+"%");
            int inValue=(int)deciValue;
         if (inValue<50){
             Toast.makeText(this, "Poor Performance You Need to Improve", Toast.LENGTH_SHORT).show();
             smilyImage.setImageResource(R.drawable.star_4);
         } else if (inValue>=50&&inValue<75)
         {
             Toast.makeText(this, "Average Performance You Can easily Improve", Toast.LENGTH_SHORT).show();
             smilyImage.setImageResource(R.drawable.star_3);

         }
         else if (inValue>=75&&inValue<85)
         {
             smilyImage.setImageResource(R.drawable.star_5);
             Toast.makeText(this, "Good Performance You Can still Scope of  Improvement", Toast.LENGTH_SHORT).show();

         }else if (inValue>=85)
         {
             smilyImage.setImageResource(R.drawable.star_2);
             Toast.makeText(this, "Excellent Performance Performance You Can still Scope of  Improvement", Toast.LENGTH_SHORT).show();

         }
        }

    }
}