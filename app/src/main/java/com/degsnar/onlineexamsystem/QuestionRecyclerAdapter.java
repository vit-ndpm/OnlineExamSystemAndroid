package com.degsnar.onlineexamsystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Question>questionArrayList;

    public QuestionRecyclerAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.question_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.q_number.setText(String.valueOf(questionArrayList.get(position).question_no));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestionColor(holder.q_number);
                setUpQuestion(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public abstract void setUpQuestion(int position);
  public void changeQuestionColor(TextView itemView){
      itemView.setTextColor(Color.WHITE);
      itemView.setBackgroundResource(R.drawable.tv3_border);

  }
    //save resposens to server color change method
    public void changeSaveReponseColor(TextView itemView){
        itemView.setTextColor(Color.WHITE);
        itemView.setBackgroundResource(R.drawable.tv2_border);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView q_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            q_number=itemView.findViewById(R.id.q_number);
        }
    }
}
