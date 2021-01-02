package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetSpecificRecruiterModel {
    @SerializedName("recruiter_fname")
    private String recruiterFname;

    @SerializedName("recruiter_lname")
    private String recruiterLname;

    @SerializedName("category_1")
    private String category1;

    @SerializedName("recruiter_id")
    private Integer recruiterId;

    @SerializedName("job_id")
    private Integer jobId;

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

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }


    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

}