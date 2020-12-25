package com.example.toes;

public class ViewRequestListModel {

    private String worker_name;
    private String worker_profession;

    public ViewRequestListModel() {
    }

    public ViewRequestListModel(String worker_name, String worker_profession) {
        this.worker_name = worker_name;
        this.worker_profession = worker_profession;
    }

    //Getter
    public String getWorker_name() {
        return worker_name;
    }
    public String getWorker_profession() {
        return worker_profession;
    }

    //Setter
    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public void setWorker_profession(String worker_profession) {
        this.worker_profession = worker_profession;
    }
}
