package com.degsnar.onlineexamsystem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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


                holder.q_number.setTextColor(Color.WHITE);
                holder.q_number.setBackgroundResource(R.drawable.tv3_border);
                setUpQuestion(holder.getAdapterPosition());
            }
        });

    }



    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public abstract void setUpQuestion(int position);


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView q_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            q_number=itemView.findViewById(R.id.q_number);
        }
    }
}
