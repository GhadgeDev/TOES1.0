package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetWorkerViewRequestModel {
    @SerializedName("job_title")
    private String jobTitle;

    @SerializedName("job_description")
    private String jobDescription;

    @SerializedName("recruiter_id")
    private Integer recruiterId;

    @SerializedName("recruiter_fname")
    private String recruiterFname;

    @SerializedName("recruiter_lname")
    private String recruiterLname;

    @SerializedName("address")
    private String address;

    @SerializedName("job_id")
    private Integer jobId;

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

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getRecruiterFname() {
        return recruiterFname;
    }

    public void setRecruiterFname(String recruiterFname) {
        this.recruiterFname = recruiterFname;
    }

    public String getRecruiterLname() {
        return recruiterLname;
    }

    public void setRecruiterLname(String recruiterLname) {
        this.recruiterLname = recruiterLname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
