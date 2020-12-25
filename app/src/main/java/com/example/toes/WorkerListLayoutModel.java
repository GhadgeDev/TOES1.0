package com.example.toes;

public class WorkerListLayoutModel {
    private String worker_name;
    private String worker_fees;

    public WorkerListLayoutModel() {
    }
    public WorkerListLayoutModel(String worker_name, String worker_fees) {
        this.worker_name = worker_name;
        this.worker_fees = worker_fees;
    }

    //Getter
    public String getWorker_name() {
        return worker_name;
    }
    public String getWorker_fees() {
        return worker_fees;
    }

    //Setter
    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public void setWorker_fees(String worker_fees) {
        this.worker_fees = worker_fees;
    }
}