package com.degsnar.onlineexamsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExamRecyclerAdapter extends RecyclerView.Adapter<ExamRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Exam>examArrayList;

    public ExamRecyclerAdapter(Context context, ArrayList<Exam> examArrayList) {
        this.context = context;
        this.examArrayList = examArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.exam_row_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.examtv.setText(examArrayList.get(position).paper_name);
        holder.exampic.setImageResource(R.drawable.baseline_account_circle_24);

    }

    @Override
    public int getItemCount() {
        return examArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView examtv;
        CircleImageView exampic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examtv=itemView.findViewById(R.id.paper_title);
            exampic=itemView.findViewById(R.id.exampic);
        }
    }
}
