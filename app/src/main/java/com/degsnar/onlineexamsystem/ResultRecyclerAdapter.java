package com.degsnar.onlineexamsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Question> questionArrayList;
    ArrayList<ResultModel> resultModelArrayList;

    public ResultRecyclerAdapter(Context context, ArrayList<Question> questionArrayList, ArrayList<ResultModel> resultModelArrayList)
    {
        this.context = context;
        this.questionArrayList = questionArrayList;
        this.resultModelArrayList = resultModelArrayList;
        Toast.makeText(context, String.valueOf(questionArrayList.size()), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.result_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.q_number.setText(questionArrayList.get(position).question_no);
        holder.question.setText(questionArrayList.get(position).question);
        holder.opt1.setText(questionArrayList.get(position).option1);
        holder.opt2.setText(questionArrayList.get(position).option2);
        holder.opt3.setText(questionArrayList.get(position).option3);
        holder.opt4.setText(questionArrayList.get(position).option4);
        holder.description.setText(questionArrayList.get(position).description);
    }

    @Override
    public int getItemCount() {

        return questionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout ql1,ql2,ql3,ql4;
        TextView q_number,question,opt1,opt2,opt3,opt4,description;
        ImageView imgOpt1,imgOpt2,imgOpt3,imgOpt4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ql1=itemView.findViewById(R.id.constraintLayoutQ1);
            ql2=itemView.findViewById(R.id.constraintLayoutQ2);
            ql3=itemView.findViewById(R.id.constraintLayoutQ3);
            ql4=itemView.findViewById(R.id.constraintLayoutQ4);
            q_number=itemView.findViewById(R.id.q_number);
            question=itemView.findViewById(R.id.question);
            opt1=itemView.findViewById(R.id.textOpt1);
            opt2=itemView.findViewById(R.id.textOpt2);
            opt3=itemView.findViewById(R.id.textOpt3);
            opt4=itemView.findViewById(R.id.textOpt4);
            description=itemView.findViewById(R.id.description);
            imgOpt1=itemView.findViewById(R.id.imgOpt1);
            imgOpt2=itemView.findViewById(R.id.imgOpt2);
            imgOpt3=itemView.findViewById(R.id.imgOpt3);
            imgOpt4=itemView.findViewById(R.id.imgOpt4);

        }
    }
}
