package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class WorkerSendPostRequest {
    @SerializedName("recruiter")
    private Object recruiter;

    @SerializedName("amount")
    private Object amount;

    @SerializedName("status")
    private Object status;

    @SerializedName("job_detail")
    private Object jobDetail;

    @SerializedName("worker")
    private Object worker;

    public Object getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Object recruiter) {
        this.recruiter = recruiter;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(Object jobDetail) {
        this.jobDetail = jobDetail;
    }

    public Object getWorker() {
        return worker;
    }

    public void setWorker(Object worker) {
        this.worker = worker;
    }
}
