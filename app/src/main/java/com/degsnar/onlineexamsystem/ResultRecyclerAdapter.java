package com.degsnar.onlineexamsystem;

import static android.graphics.Color.GREEN;
import static com.degsnar.onlineexamsystem.R.drawable.tv_correct_border;
import static com.degsnar.onlineexamsystem.R.drawable.tv_wrong_border;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Question> questionArrayList;
    ArrayList<UserResponse> userResponseArrayList;

    public ResultRecyclerAdapter(Context context, ArrayList<Question> questionArrayList, ArrayList<UserResponse> userResponseArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
        this.userResponseArrayList = userResponseArrayList;
        // Toast.makeText(context, "Questions: " + questionArrayList.size(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(context, "Response: " + userResponseArrayList.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.q_number.setText(String.valueOf(questionArrayList.get(position).question_no));
        holder.question.setText(questionArrayList.get(position).question);
        holder.opt1.setText(String.valueOf(questionArrayList.get(position).option1));
        holder.opt2.setText(String.valueOf(questionArrayList.get(position).option2));
        holder.opt3.setText(String.valueOf(questionArrayList.get(position).option3));
        holder.opt4.setText(String.valueOf(questionArrayList.get(position).option4));
        holder.correctOption.setText(String.valueOf(questionArrayList.get(position).correct_option));
        holder.descripition.setText(questionArrayList.get(position).description);
        for (int i = 0; i <userResponseArrayList.size() ; i++) {
            if (userResponseArrayList.get(i).question_id==questionArrayList.get(position).id){
                holder.selectedOption.setText(String.valueOf(userResponseArrayList.get(i).selected_option));
                if (userResponseArrayList.get(i).selected_option==questionArrayList.get(position).correct_option){
                    holder.selectedOption.setTextColor(GREEN);
                    holder.selectedOption.setTextSize(20);
                    holder.img5.setVisibility(View.VISIBLE);
                    holder.img5.setImageResource(R.drawable.correct);
                }
            }
        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        holder.itemView.startAnimation(animation);





    }


    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView q_number, question, opt1, opt2, opt3, opt4, descripition, correctOption,selectedOption;
        ImageView img1, img2, img3, img4,img5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            q_number = itemView.findViewById(R.id.q_numberTv);
            question = itemView.findViewById(R.id.questionTv);
            opt1 = itemView.findViewById(R.id.opt1Tv);
            opt2 = itemView.findViewById(R.id.opt2Tv);
            opt3 = itemView.findViewById(R.id.opt3Tv);
            opt4 = itemView.findViewById(R.id.opt4Tv);
            correctOption = itemView.findViewById(R.id.correctOptionTv);
            selectedOption = itemView.findViewById(R.id.selectedOption);

            descripition = itemView.findViewById(R.id.descriptionTv);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
        }
    }
}
