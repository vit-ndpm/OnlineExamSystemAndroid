package com.degsnar.onlineexamsystem;

public class UserResponse {
    int id, user_id, question_id, paper_id, selected_option;

    public UserResponse() {
    }

    public UserResponse(int id, int user_id, int question_id, int paper_id, int selected_option) {
        this.id = id;
        this.user_id = user_id;
        this.question_id = question_id;
        this.paper_id = paper_id;
        this.selected_option = selected_option;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public int getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(int selected_option) {
        this.selected_option = selected_option;
    }
}
