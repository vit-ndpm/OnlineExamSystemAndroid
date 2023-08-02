package com.degsnar.onlineexamsystem;

import static com.degsnar.onlineexamsystem.R.drawable.tv_correct_border;
import static com.degsnar.onlineexamsystem.R.drawable.tv_wrong_border;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Question> questionArrayList;
    ArrayList<ResultModel> resultModelArrayList;

    public ResultRecyclerAdapter(Context context, ArrayList<Question> questionArrayList, ArrayList<ResultModel> resultModelArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
        this.resultModelArrayList = resultModelArrayList;
        Toast.makeText(context, "Questions: " + questionArrayList.size(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Response: " + resultModelArrayList.size(), Toast.LENGTH_SHORT).show();
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
        int questionId = questionArrayList.get(position).id;
        if (checkIfSelectedCorrectOption(questionId) <4) {
            int selectedOption = checkIfSelectedCorrectOption(questionId);
            switch (selectedOption) {
                case 1:
                    holder.img1.setVisibility(View.VISIBLE);
                    holder.img1.setImageResource(R.drawable.correct);
                    holder.opt1.setBackgroundResource(tv_correct_border);
                    break;
                case 2:
                    holder.img2.setVisibility(View.VISIBLE);
                    holder.img2.setImageResource(R.drawable.correct);
                    holder.opt2.setBackgroundResource(tv_correct_border);
                    break;
                case 3:
                    holder.img3.setVisibility(View.VISIBLE);
                    holder.img3.setImageResource(R.drawable.correct);
                    holder.opt3.setBackgroundResource(tv_correct_border);
                    break;
                case 4:
                    holder.img4.setVisibility(View.VISIBLE);
                    holder.img4.setImageResource(R.drawable.correct);
                    holder.opt4.setBackgroundResource(tv_correct_border);
                    break;
                default:
                    break;
            }
        }else {
            int selectedOption = checkIfSelectedCorrectOption(questionId);
            switch (selectedOption) {
                case 101:
                    holder.img1.setVisibility(View.VISIBLE);
                    holder.img1.setImageResource(R.drawable.baseline_close_24);
                    holder.opt1.setBackgroundResource(tv_wrong_border);
                    break;
                case 102:
                    holder.img2.setVisibility(View.VISIBLE);
                    holder.img2.setImageResource(R.drawable.baseline_close_24);
                    holder.opt2.setBackgroundResource(tv_wrong_border);
                    break;
                case 103:
                    holder.img3.setVisibility(View.VISIBLE);
                    holder.img3.setImageResource(R.drawable.baseline_close_24);
                    holder.opt3.setBackgroundResource(tv_wrong_border);
                    break;
                case 104:
                    holder.img4.setVisibility(View.VISIBLE);
                    holder.img4.setImageResource(R.drawable.baseline_close_24);
                    holder.opt4.setBackgroundResource(tv_wrong_border);
                    break;
                default:
                    break;
            }

        }


    }

    private int checkIfSelectedCorrectOption(int questionId) {
        for (int i = 0; i < resultModelArrayList.size(); i++) {
            ResultModel resultModel = resultModelArrayList.get(i);
            if (resultModel.question_id == questionId && resultModel.correct_option == resultModel.selected_option) {
                return resultModel.correct_option;
            } else if(resultModel.question_id == questionId && resultModel.correct_option != resultModel.selected_option){
                return 100+resultModel.selected_option;
            }

        }
        return 10000;
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView q_number, question, opt1, opt2, opt3, opt4, descripition, correctOption;
        ImageView img1, img2, img3, img4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            q_number = itemView.findViewById(R.id.q_numberTv);
            question = itemView.findViewById(R.id.questionTv);
            opt1 = itemView.findViewById(R.id.opt1Tv);
            opt2 = itemView.findViewById(R.id.opt2Tv);
            opt3 = itemView.findViewById(R.id.opt3Tv);
            opt4 = itemView.findViewById(R.id.opt4Tv);
            correctOption = itemView.findViewById(R.id.correctOptionTv);
            descripition = itemView.findViewById(R.id.descriptionTv);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
        }
    }
}
