package com.degsnar.onlineexamsystem;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.ColorInt;
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

       if (examArrayList.get(position).paper_type.equals("1")){
           holder.examTypeTV.setText("Available");
           holder.examTypeTV.setTextColor(GREEN);
       }
       else{
           holder.examTypeTV.setText("Not Published");
           holder.examTypeTV.setTextColor(RED);
       }

       if (examArrayList.get(position).available.equals("1")){
           holder.availabilityTv.setText("Free");
           holder.availabilityTv.setTextColor(BLUE);
       }
       else{
           holder.availabilityTv.setText("Paid");
           holder.availabilityTv.setTextColor(BLUE);
       }

        holder.exampic.setImageResource(R.drawable.baseline_account_circle_24);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paperName=examArrayList.get(holder.getAdapterPosition()).paper_name;
                int paperId= examArrayList.get(holder.getAdapterPosition()).id;
                Toast.makeText(context, paperId+":"+paperName, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, Paper.class);
                myIntent.putExtra("paperId", paperId);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(myIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return examArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView examtv,examTypeTV,availabilityTv;
        CircleImageView exampic;
        ImageView imageView3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examtv=itemView.findViewById(R.id.paper_title);
            availabilityTv=itemView.findViewById(R.id.paepr_type);
            examTypeTV=itemView.findViewById(R.id.available);
            exampic=itemView.findViewById(R.id.exampic);
        }
    }
}
