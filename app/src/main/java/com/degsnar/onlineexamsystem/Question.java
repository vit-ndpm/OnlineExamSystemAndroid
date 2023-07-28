package com.degsnar.onlineexamsystem;

public class Question {
    int id, question_no, paper_id, correct_option, subject_id, topic_id;
    String question, option1, option2, option3, option4, description;

    public Question(int id, int question_no, int paper_id, int correct_option, int subject_id, int topic_id, String question, String option1, String option2, String option3, String option4, String description) {
        this.id = id;
        this.question_no = question_no;
        this.paper_id = paper_id;
        this.correct_option = correct_option;
        this.subject_id = subject_id;
        this.topic_id = topic_id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.description = description;
    }

    public Question() {
    }

    public Question(int id, int question_no) {

        this.question_no = question_no;
    }

    public Question(int id, int question_no, int correct_option, String question, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.question_no = question_no;
        this.correct_option = correct_option;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_no() {
        return question_no;
    }

    public void setQuestion_no(int question_no) {
        this.question_no = question_no;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public int getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(int correct_option) {
        this.correct_option = correct_option;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
