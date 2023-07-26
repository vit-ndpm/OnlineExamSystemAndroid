package com.degsnar.onlineexamsystem;

public class Exam {
    int id;
    String paper_name,paper_type,available,created_at,updated_at;

    public Exam(int id, String paper_name, String paper_type, String available, String created_at, String updated_at) {
        this.id = id;
        this.paper_name = paper_name;
        this.paper_type = paper_type;
        this.available = available;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Exam(String paper_name) {
        this.paper_name = paper_name;
    }

    public Exam(int id, String paper_name, String paper_type, String available) {
        this.id = id;
        this.paper_name = paper_name;
        this.paper_type = paper_type;
        this.available = available;
    }

    public Exam(int id, String paper_name) {
        this.id = id;
        this.paper_name = paper_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
