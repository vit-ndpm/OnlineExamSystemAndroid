package com.degsnar.onlineexamsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    String UserToken;
    ArrayList<Question> questionArrayList;
    ArrayList<UserResponse> responseArrayList;
    ArrayList<ResultModel>resultArrayList;
    Button seeResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        seeResult=findViewById(R.id.seeResult);
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

        getAllQuestions();
        getAllResponse();
        seeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
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
                                        Log.d("total Questions:",String.valueOf(questionArrayList.size()));

                                    }


                                }
                                // Toast.makeText(Paper.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Result.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Result.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void getAllResponse() {
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


                            } else if (response.getString("status").equals("failed")) {
                                Toast.makeText(Result.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error", anError.getLocalizedMessage());
                        Toast.makeText(Result.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void calculateResult() {
        int counter=0;
        for (int i = 0; i < questionArrayList.size(); i++) {
            int currentQuestionId=questionArrayList.get(i).getId();
            for (int j = 0; j <responseArrayList.size() ; j++) {
                if (currentQuestionId==responseArrayList.get(j).question_id){
                    ResultModel resultModel=new ResultModel();
                    resultModel.question_id=questionArrayList.get(i).id;
                    resultModel.response_id=responseArrayList.get(j).id;
                    resultModel.correct_option=questionArrayList.get(i).correct_option;
                    resultModel.selected_option=responseArrayList.get(j).selected_option;
                    resultArrayList.add(resultModel);
                    Log.d("Result Model","selected Option: "+resultModel.selected_option+" Correct Option: "+resultModel.correct_option);

                }
            }


        }
    }
}