package com.degsnar.onlineexamsystem;

public class ResultModel {
    int question_id;
    int response_id;
    int selected_option;
    int correct_option;

    public ResultModel(int question_id, int response_id, int selected_option, int correct_option) {
        this.question_id = question_id;
        this.response_id = response_id;
        this.selected_option = selected_option;
        this.correct_option = correct_option;
    }

    public ResultModel() {
    }
}
