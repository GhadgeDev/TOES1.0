package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class RecruiterHirePostRequest {
    @SerializedName("id")
    private Integer id;

    @SerializedName("recruiter")
    private Integer recruiter;

    @SerializedName("status")
    private Integer status;

    @SerializedName("job_detail")
    private Integer jobDetail;

    @SerializedName("worker")
    private Integer worker;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Integer recruiter) {
        this.recruiter = recruiter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(Integer jobDetail) {
        this.jobDetail = jobDetail;
    }

    public Integer getWorker() {
        return worker;
    }

    public void setWorker(Integer worker) {
        this.worker = worker;
    }

}
