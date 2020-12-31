package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetRecruiterJobInfo {

    @SerializedName("id")
    private Integer id;

    @SerializedName("job_title")
    private String jobTitle;

    @SerializedName("job_Description")
    private String jobDescription;

    @SerializedName("status")
    private Integer status;

    @SerializedName("recruiter")
    private Integer recruiter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Integer recruiter) {
        this.recruiter = recruiter;
    }

}
